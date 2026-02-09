package com.wego.seolstudybe.task.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MenteeSubmissionSummaryResponse {
    private int menteeId;
    private String profileUrl;
    private String menteeName;
    private int assignedCount;
    private int submittedCount;
}
