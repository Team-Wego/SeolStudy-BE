package com.wego.seolstudybe.task.dto.response;

import com.wego.seolstudybe.mentoring.entity.enums.Subject;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class StudyTimeDetailDto {

    private int id;
    private int taskId;
    private String taskTitle;
    private Subject subject;
    private LocalDateTime startedAt;
    private LocalDateTime endedAt;
}
