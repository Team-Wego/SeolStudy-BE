package com.wego.seolstudybe.mentoring.service;

import com.wego.seolstudybe.mentoring.dto.MenteeResponse;

import java.util.List;

public interface MentoringService {
    List<MenteeResponse> getMenteeList(final int mentorId);
}