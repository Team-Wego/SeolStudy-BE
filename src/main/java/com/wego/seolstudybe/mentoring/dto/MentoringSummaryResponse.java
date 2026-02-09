package com.wego.seolstudybe.mentoring.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class MentoringSummaryResponse {
    private int menteeCount;
    private int pendingFeedbackCount;
}