package com.wego.seolstudybe.task.dao;

import com.wego.seolstudybe.mentoring.entity.enums.Subject;
import com.wego.seolstudybe.task.dto.response.*;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDate;
import java.util.List;

@Mapper
public interface TaskMapper {

    List<TaskListResponse> findTasksByMenteeIdAndDateRange(
            @Param("menteeId") int menteeId,
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate
    );

    List<DailyTaskResponse> findDailyTasksByMenteeIdAndDate(
            @Param("menteeId") int menteeId,
            @Param("date") LocalDate date
    );

    TaskDetailResponse findTaskById(@Param("taskId") int taskId);

    List<WorksheetFileDto> findWorksheetFilesByTaskId(@Param("taskId") int taskId);

    List<TaskImageDto> findImagesByTaskId(@Param("taskId") int taskId);

    PlannerCommentResponse findPlannerCommentByMenteeIdAndDate(
            @Param("menteeId") int menteeId,
            @Param("date") LocalDate date
    );

    DailyStudyTimeResponse findStudyTimeByMenteeIdAndDate(
            @Param("menteeId") int menteeId,
            @Param("date") LocalDate date
    );

    List<StudyTimeDetailDto> findStudyTimeDetailsByMenteeIdAndDate(
            @Param("menteeId") int menteeId,
            @Param("date") LocalDate date
    );

    List<SubjectStudyStatusResponse> findStudyStatusByMenteeIdAndDateRange(
            @Param("menteeId") int menteeId,
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate,
            @Param("subject") Subject subject
    );
}
