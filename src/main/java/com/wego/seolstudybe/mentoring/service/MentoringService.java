package com.wego.seolstudybe.mentoring.service;

import com.wego.seolstudybe.mentoring.dto.MenteeResponse;
import com.wego.seolstudybe.mentoring.dto.MentoringSummaryResponse;

import java.util.List;

public interface MentoringService {
    MentoringSummaryResponse getMentoringSummary(final int mentorId);

    List<MenteeResponse> getMenteeList(final int mentorId);
}