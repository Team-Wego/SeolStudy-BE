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
import com.wego.seolstudybe.task.dto.CreateTaskRequest;
import com.wego.seolstudybe.task.dto.TaskResponse;
import com.wego.seolstudybe.task.entity.Task;
import com.wego.seolstudybe.task.entity.TaskWorksheet;
import com.wego.seolstudybe.task.repository.TaskRepository;
import com.wego.seolstudybe.task.repository.TaskWorksheetRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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
    public TaskResponse createTask(
            int mentorId,
            int menteeId,
            CreateTaskRequest request
    ) {

        // 멘티 조회
        Member mentee = memberRepository.findById(menteeId)
                .orElseThrow(MemberNotFoundException::new);

        // 멘토 검증
        if (mentee.getMentor() == null || mentee.getMentor().getId() != mentorId) {
            throw new NotAssignedMenteeException();
        }

        // 목표 검증
        Goal goal = null;
        if (request.getGoalId() != null) {
            goal = goalRepository.findById(request.getGoalId())
                    .orElseThrow(GoalNotFoundException::new);
        }

        // task 생성 (ASSIGNMENT)
        int nextSequence = taskRepository
                .findMaxSequenceByMenteeAndDate(menteeId, request.getDate()) + 1;

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

        // 학습지 연결
        if (request.getWorksheetFileIds() != null) {
            for (Integer fileId : request.getWorksheetFileIds()) {
                WorksheetFile file =
                        worksheetFileRepository.findById(fileId)
                                .orElseThrow(WorksheetNotFoundException::new);

                if (file.getMentee().getId() != menteeId) {
                    throw new WorksheetNotOwnedException();
                }

                taskWorksheetRepository.save(
                        new TaskWorksheet(task, file)
                );
            }
        }

        return new TaskResponse(
                task.getId(),
                mentee.getId(),
                task.getTitle(),
                task.getType(),
                task.getDate(),
                task.getSubject()
        );
    }
}
