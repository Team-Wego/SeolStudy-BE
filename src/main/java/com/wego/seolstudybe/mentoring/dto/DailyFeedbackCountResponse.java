package com.wego.seolstudybe.mentoring.dto;

import java.time.LocalDate;

public interface DailyFeedbackCountResponse {
    LocalDate getDate();

    Long getCount();
}