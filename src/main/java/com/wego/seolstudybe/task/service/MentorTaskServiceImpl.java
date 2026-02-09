package com.wego.seolstudybe.task.service;

import com.wego.seolstudybe.member.entity.Member;
import com.wego.seolstudybe.member.exception.MemberNotFoundException;
import com.wego.seolstudybe.member.exception.NotAssignedMenteeException;
import com.wego.seolstudybe.member.repository.MemberRepository;
import com.wego.seolstudybe.mentoring.entity.Goal;
import com.wego.seolstudybe.mentoring.entity.WorksheetFile;
import com.wego.seolstudybe.mentoring.exception.GoalNotFoundException;
import com.wego.seolstudybe.mentoring.exception.WorksheetNotFoundException;
import com.wego.seolstudybe.mentoring.exception.WorksheetNotOwnedException;
import com.wego.seolstudybe.mentoring.repository.GoalRepository;
import com.wego.seolstudybe.mentoring.repository.WorksheetFileRepository;
import com.wego.seolstudybe.task.dto.request.CreateTaskRequest;
import com.wego.seolstudybe.task.dto.request.UpdateTaskRequest;
import com.wego.seolstudybe.task.dto.response.CreateTaskResponse;
import com.wego.seolstudybe.task.dto.response.MenteeSubmissionSummaryResponse;
import com.wego.seolstudybe.task.dto.response.PendingFeedbackResponse;
import com.wego.seolstudybe.task.dto.response.UpdateTaskResponse;
import com.wego.seolstudybe.task.entity.Task;
import com.wego.seolstudybe.task.entity.TaskWorksheet;
import com.wego.seolstudybe.task.entity.enums.TaskType;
import com.wego.seolstudybe.task.exception.TaskNotFoundException;
import com.wego.seolstudybe.task.repository.TaskRepository;
import com.wego.seolstudybe.task.repository.TaskWorksheetRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MentorTaskServiceImpl implements MentorTaskService{

    private final MemberRepository memberRepository;
    private final TaskRepository taskRepository;
    private final GoalRepository goalRepository;
    private final WorksheetFileRepository worksheetFileRepository;
    private final TaskWorksheetRepository taskWorksheetRepository;

    @Override
    @Transactional
    public CreateTaskResponse createTask(
            int mentorId,
            int menteeId,
            CreateTaskRequest request
    ) {

        Member mentee = getVerifiedMentee(mentorId, menteeId);
        Goal goal = getGoalOrNull(request.getGoalId());

        int nextSequence = taskRepository
                .findMaxSequenceByMenteeIdAndDate(mentee.getId(), request.getDate()) + 1;

        Task task = Task.builder()
                .mentee(mentee)
                .goal(goal)
                .title(request.getTitle())
                .description(request.getDescription())
                .type(request.getType())
                .date(request.getDate())
                .subject(request.getSubject())
                .sequence(nextSequence)
                .build();

        taskRepository.save(task);

        replaceTaskWorksheets(task, mentee, request.getWorksheetFileIds());

        return new CreateTaskResponse(
                task.getId()
        );
    }

    @Override
    @Transactional
    public UpdateTaskResponse updateTask(
            int mentorId,
            int taskId,
            UpdateTaskRequest request
    ) {

        Task task = taskRepository.findById(taskId)
                .orElseThrow(TaskNotFoundException::new);

        Member mentee = getVerifiedMentee(mentorId, task.getMentee().getId());
        Goal goal = getGoalOrNull(request.getGoalId());

        task.update(
                request.getTitle(),
                request.getDescription(),
                request.getType(),
                request.getDate(),
                request.getSubject(),
                goal
        );

        replaceTaskWorksheets(task, mentee, request.getWorksheetFileIds());

        return new UpdateTaskResponse(
                task.getId(),
                mentee.getId(),
                task.getTitle(),
                task.getType(),
                task.getDate(),
                task.getSubject()
        );

    }

    @Override
    public void deleteTask(int mentorId, int taskId) {

        Task task = taskRepository.findById(taskId)
                .orElseThrow(TaskNotFoundException::new);

        Member mentee = getVerifiedMentee(mentorId, task.getMentee().getId());

        taskWorksheetRepository.deleteByTask(task);
        taskRepository.delete(task);
    }

    @Override
    public List<MenteeSubmissionSummaryResponse> getSubmissionSummary(int mentorId) {
        List<Member> mentees = memberRepository.findByMentorId(mentorId);
        LocalDate today = LocalDate.now();

        return mentees.stream()
                .map(mentee -> {
                    int assignedCount = taskRepository.countByMenteeIdAndDateAndType(
                            mentee.getId(), today, TaskType.ASSIGNMENT);
                    int submittedCount = taskRepository.countByMenteeIdAndDateAndTypeAndSubmittedAtIsNotNull(
                            mentee.getId(), today, TaskType.ASSIGNMENT);

                    return new MenteeSubmissionSummaryResponse(
                            mentee.getId(),
                            mentee.getProfileUrl(),
                            mentee.getName(),
                            assignedCount,
                            submittedCount
                    );
                })
                .toList();
    }

    @Override
    public List<PendingFeedbackResponse> getPendingFeedbacks(int mentorId) {
        List<Member> mentees = memberRepository.findByMentorId(mentorId);
        List<Integer> menteeIds = mentees.stream()
                .map(Member::getId)
                .toList();

        List<Task> tasks = taskRepository
                .findTop10ByMenteeIdInAndHasFeedbackFalseAndSubmittedAtIsNotNullOrderBySubmittedAtAsc(menteeIds);

        return tasks.stream()
                .map(task -> new PendingFeedbackResponse(
                        task.getId(),
                        task.getTitle(),
                        task.getComment(),
                        task.getSubmittedAt(),
                        task.getMentee().getName(),
                        task.getType(),
                        task.getSubject(),
                        task.getMentee().getProfileUrl()
                ))
                .toList();
    }


    /**
     * 멘토가 해당 멘티의 담당자인지 검증
     */
    private Member getVerifiedMentee(int mentorId, int menteeId) {
        Member mentee = memberRepository.findById(menteeId)
                .orElseThrow(MemberNotFoundException::new);

        if (mentee.getMentor() == null || mentee.getMentor().getId() != mentorId) {
            throw new NotAssignedMenteeException();
        }
        return mentee;
    }

    /**
     * goalId가 있을 경우 Goal 조회, 없으면 null 반환
     */
    private Goal getGoalOrNull(Integer goalId) {
        if (goalId == null) {
            return null;
        }
        return goalRepository.findById(goalId)
                .orElseThrow(GoalNotFoundException::new);
    }

    /**
     * Task에 연결된 학습지를 모두 삭제한 뒤, 요청으로 전달된 학습지 목록으로 다시 연결
     */
    private void replaceTaskWorksheets(
            Task task,
            Member mentee,
            List<Integer> worksheetFileIds
    ) {
        taskWorksheetRepository.deleteByTask(task);

        if (worksheetFileIds == null || worksheetFileIds.isEmpty()) {
            return;
        }

        for (Integer fileId : worksheetFileIds) {
            WorksheetFile file = worksheetFileRepository.findById(fileId)
                    .orElseThrow(WorksheetNotFoundException::new);

            if (file.getMentee().getId() != mentee.getId()) {
                throw new WorksheetNotOwnedException();
            }

            taskWorksheetRepository.save(new TaskWorksheet(task, file));
        }
    }

}
