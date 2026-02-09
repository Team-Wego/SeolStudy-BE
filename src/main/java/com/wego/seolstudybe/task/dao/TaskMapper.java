package com.wego.seolstudybe.task.dao;

import com.wego.seolstudybe.mentoring.entity.enums.Subject;
import com.wego.seolstudybe.task.dto.response.*;
import com.wego.seolstudybe.task.entity.enums.TaskType;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDate;
import java.util.List;

@Mapper
public interface TaskMapper {

    List<TaskListResponse> findTasksByMenteeIdAndDateRange(
            @Param("menteeId") int menteeId,
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate,
            @Param("taskType") TaskType taskType
    );

    List<DailyTaskResponse> findDailyTasksByMenteeIdAndDate(
            @Param("menteeId") int menteeId,
            @Param("date") LocalDate date
    );

    TaskDetailResponse findTaskById(@Param("taskId") int taskId);

    List<WorksheetFileDto> findWorksheetFilesByTaskId(@Param("taskId") int taskId);

    List<TaskImageDto> findImagesByTaskId(@Param("taskId") int taskId);

    GetPlannerCommentDto findPlannerCommentByMenteeIdAndDate(
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
            @Param("subject") Subject subject,
            @Param("taskType") TaskType taskType
    );

    List<DailyTaskStatusResponse> findDailyTaskStatusByMenteeIdAndDateRange(@Param("menteeId") final int menteeId,
                                                                            @Param("startDate") final LocalDate startDate,
                                                                            @Param("endDate") final LocalDate endDate,
                                                                            @Param("type") final TaskType taskType
    );
}
