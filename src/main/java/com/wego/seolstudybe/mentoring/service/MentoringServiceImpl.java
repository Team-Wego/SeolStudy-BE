package com.wego.seolstudybe.mentoring.service;

import com.wego.seolstudybe.member.entity.Member;
import com.wego.seolstudybe.member.exception.MemberNotFoundException;
import com.wego.seolstudybe.member.repository.MemberRepository;
import com.wego.seolstudybe.mentoring.dto.MenteeResponse;
import com.wego.seolstudybe.mentoring.dto.MentoringSummaryResponse;
import com.wego.seolstudybe.task.entity.enums.TaskType;
import com.wego.seolstudybe.task.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class MentoringServiceImpl implements MentoringService {
    private final MemberRepository memberRepository;
    private final TaskRepository taskRepository;

    @Transactional(readOnly = true)
    @Override
    public MentoringSummaryResponse getMentoringSummary(final int mentorId) {
        final int menteeCount = memberRepository.countByMentorIdAndEndedAtIsNull(mentorId);

        final List<Integer> menteeIds = memberRepository.findByMentorId(mentorId).stream()
                .map(Member::getId)
                .toList();

        final int pendingFeedbackCount = menteeIds.isEmpty()
                ? 0
                : taskRepository.countByMenteeIdInAndHasFeedbackFalseAndSubmittedAtIsNotNullAndType(menteeIds,
                TaskType.ASSIGNMENT);

        return new MentoringSummaryResponse(menteeCount, pendingFeedbackCount);
    }

    @Transactional(readOnly = true)
    @Override
    public List<MenteeResponse> getMenteeList(final int mentorId) {
        memberRepository.findById(mentorId).orElseThrow(MemberNotFoundException::new);

        return memberRepository.findByMentorId(mentorId).stream()
                .map(MenteeResponse::of)
                .toList();
    }
}