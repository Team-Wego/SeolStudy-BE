package com.wego.seolstudybe.task.dto.response;

import com.wego.seolstudybe.mentoring.entity.enums.Subject;
import com.wego.seolstudybe.task.entity.enums.TaskType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PendingFeedbackResponse {
    private int taskId;
    private String title;
    private String comment;
    private LocalDateTime submittedAt;
    private String menteeName;
    private TaskType type;
    private Subject subject;
    private String menteeProfileUrl;
    private int menteeId;
}
