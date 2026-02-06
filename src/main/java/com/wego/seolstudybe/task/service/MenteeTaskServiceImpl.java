package com.wego.seolstudybe.task.service;

import com.wego.seolstudybe.member.entity.Member;
import com.wego.seolstudybe.member.exception.MemberNotFoundException;
import com.wego.seolstudybe.member.repository.MemberRepository;
import com.wego.seolstudybe.task.dto.request.TaskCreateRequest;
import com.wego.seolstudybe.task.dto.request.TaskSequenceUpdateRequest;
import com.wego.seolstudybe.task.dto.request.TaskStatusUpdateRequest;
import com.wego.seolstudybe.task.dto.request.TaskUpdateRequest;
import com.wego.seolstudybe.task.dto.response.TaskResponse;
import com.wego.seolstudybe.task.entity.Task;
import com.wego.seolstudybe.task.entity.enums.TaskType;
import com.wego.seolstudybe.task.exception.TaskNotFoundException;
import com.wego.seolstudybe.task.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MenteeTaskServiceImpl implements MenteeTaskService {

    private final TaskRepository taskRepository;
    private final MemberRepository memberRepository;

    @Override
    @Transactional
    public TaskResponse createTask(int memberId, TaskCreateRequest request) {
        Member mentee = memberRepository.findById(memberId)
                .orElseThrow(MemberNotFoundException::new);

        int nextSequence = taskRepository.findMaxSequenceByMenteeIdAndDate(memberId, request.getDate()) + 1;

        Task task = Task.builder()
                .mentee(mentee)
                .title(request.getTitle())
                .type(TaskType.TODO)
                .date(request.getDate())
                .subject(request.getSubject())
                .sequence(nextSequence)
                .createdAt(LocalDateTime.now())
                .build();

        Task savedTask = taskRepository.save(task);
        return TaskResponse.from(savedTask);
    }

    @Override
    @Transactional
    public TaskResponse updateTask(int memberId, int taskId, TaskUpdateRequest request) {
        Task task = findTaskByIdAndMemberId(taskId, memberId);
        task.updateContent(request.getTitle(), request.getSubject());
        return TaskResponse.from(task);
    }

    @Override
    @Transactional
    public void updateTaskSequence(int memberId, int taskId, TaskSequenceUpdateRequest request) {
        Task task = findTaskByIdAndMemberId(taskId, memberId);
        int oldSequence = task.getSequence();
        int newSequence = request.getSequence();

        if (oldSequence == newSequence) {
            return;
        }

        List<Task> tasks = taskRepository.findByMenteeIdAndDateOrderBySequenceAsc(memberId, task.getDate());

        if (oldSequence > newSequence) {
            // 위로 이동: newSequence ~ oldSequence-1 사이의 task들 +1
            for (Task t : tasks) {
                if (t.getSequence() >= newSequence && t.getSequence() < oldSequence) {
                    t.updateSequence(t.getSequence() + 1);
                }
            }
        } else {
            // 아래로 이동: oldSequence+1 ~ newSequence 사이의 task들 -1
            for (Task t : tasks) {
                if (t.getSequence() > oldSequence && t.getSequence() <= newSequence) {
                    t.updateSequence(t.getSequence() - 1);
                }
            }
        }

        task.updateSequence(newSequence);
    }

    @Override
    @Transactional
    public void deleteTask(int memberId, int taskId) {
        Task task = findTaskByIdAndMemberId(taskId, memberId);
        taskRepository.delete(task);
    }

    @Override
    @Transactional
    public TaskResponse updateTaskStatus(int memberId, int taskId, TaskStatusUpdateRequest request) {
        Task task = findTaskByIdAndMemberId(taskId, memberId);
        task.changeStatus(request.getIsChecked());
        return TaskResponse.from(task);
    }

    private Task findTaskByIdAndMemberId(int taskId, int memberId) {
        return taskRepository.findByIdAndMenteeId(taskId, memberId)
                .orElseThrow(TaskNotFoundException::new);
    }
}
