package com.wego.seolstudybe.task.service;

import com.wego.seolstudybe.task.dto.request.StudyTimeCreateRequest;
import com.wego.seolstudybe.task.dto.request.StudyTimeUpdateRequest;
import com.wego.seolstudybe.task.dto.response.StudyTimeResponse;
import com.wego.seolstudybe.task.entity.StudyTime;
import com.wego.seolstudybe.task.entity.Task;
import com.wego.seolstudybe.task.exception.StudyTimeNotFoundException;
import com.wego.seolstudybe.task.exception.TaskNotFoundException;
import com.wego.seolstudybe.task.repository.StudyTimeRepository;
import com.wego.seolstudybe.task.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class StudyTimeServiceImpl implements StudyTimeService {

    private final StudyTimeRepository studyTimeRepository;
    private final TaskRepository taskRepository;

    @Override
    @Transactional
    public StudyTimeResponse createStudyTime(int menteeId, StudyTimeCreateRequest request) {
        Task task = taskRepository.findByIdAndMenteeId(request.getTaskId(), menteeId)
                .orElseThrow(TaskNotFoundException::new);

        StudyTime studyTime = StudyTime.builder()
                .task(task)
                .startedAt(request.getStartedAt())
                .endedAt(request.getEndedAt())
                .build();

        StudyTime savedStudyTime = studyTimeRepository.save(studyTime);
        return StudyTimeResponse.from(savedStudyTime);
    }

    @Override
    @Transactional
    public StudyTimeResponse updateStudyTime(int menteeId, int studyTimeId, StudyTimeUpdateRequest request) {
        StudyTime studyTime = findStudyTimeByIdAndMenteeId(studyTimeId, menteeId);
        studyTime.update(request.getStartedAt(), request.getEndedAt());
        return StudyTimeResponse.from(studyTime);
    }

    @Override
    @Transactional
    public void deleteStudyTime(int menteeId, int studyTimeId) {
        StudyTime studyTime = findStudyTimeByIdAndMenteeId(studyTimeId, menteeId);
        studyTimeRepository.delete(studyTime);
    }

    private StudyTime findStudyTimeByIdAndMenteeId(int studyTimeId, int menteeId) {
        return studyTimeRepository.findByIdAndTaskMenteeId(studyTimeId, menteeId)
                .orElseThrow(StudyTimeNotFoundException::new);
    }
}
