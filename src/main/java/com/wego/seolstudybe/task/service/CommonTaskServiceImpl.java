package com.wego.seolstudybe.task.service;

import com.wego.seolstudybe.task.dto.response.*;
import com.wego.seolstudybe.task.exception.TaskNotFoundException;
import com.wego.seolstudybe.task.dao.TaskMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CommonTaskServiceImpl implements CommonTaskService {

    private final TaskMapper taskMapper;

    @Override
    @Transactional(readOnly = true)
    public List<TaskListResponse> getTasksByDateRange(int menteeId, LocalDate startDate, LocalDate endDate) {
        return taskMapper.findTasksByMenteeIdAndDateRange(menteeId, startDate, endDate);
    }

    @Override
    @Transactional(readOnly = true)
    public List<DailyTaskResponse> getDailyTasks(int menteeId, LocalDate date) {
        return taskMapper.findDailyTasksByMenteeIdAndDate(menteeId, date);
    }

    @Override
    @Transactional(readOnly = true)
    public TaskDetailResponse getTaskDetail(int taskId) {
        TaskDetailResponse task = taskMapper.findTaskById(taskId);
        if (task == null) {
            throw new TaskNotFoundException();
        }

        List<WorksheetFileDto> worksheetFiles = taskMapper.findWorksheetFilesByTaskId(taskId);
        List<TaskImageDto> images = taskMapper.findImagesByTaskId(taskId);

        task.setWorksheetFiles(worksheetFiles != null ? worksheetFiles : Collections.emptyList());
        task.setImages(images != null ? images : Collections.emptyList());

        return task;
    }

    @Override
    @Transactional(readOnly = true)
    public PlannerCommentResponse getPlannerComment(int menteeId, LocalDate date) {
        return taskMapper.findPlannerCommentByMenteeIdAndDate(menteeId, date);
    }

    @Override
    @Transactional(readOnly = true)
    public DailyStudyTimeResponse getStudyTime(int menteeId, LocalDate date) {
        DailyStudyTimeResponse response = taskMapper.findStudyTimeByMenteeIdAndDate(menteeId, date);
        if (response == null) {
            response = new DailyStudyTimeResponse();
            response.setDate(date);
            response.setTotalMinutes(0);
            response.setStudyTimes(Collections.emptyList());
        } else {
            List<StudyTimeDetailDto> studyTimeDetails = taskMapper.findStudyTimeDetailsByMenteeIdAndDate(menteeId, date);
            response.setStudyTimes(studyTimeDetails != null ? studyTimeDetails : Collections.emptyList());
        }
        return response;
    }
}
