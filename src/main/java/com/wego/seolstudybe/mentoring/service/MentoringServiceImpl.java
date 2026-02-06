package com.wego.seolstudybe.mentoring.service;

import com.wego.seolstudybe.member.exception.MemberNotFoundException;
import com.wego.seolstudybe.member.repository.MemberRepository;
import com.wego.seolstudybe.mentoring.dto.MenteeResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class MentoringServiceImpl implements MentoringService {
    private final MemberRepository memberRepository;

    @Transactional(readOnly = true)
    @Override
    public List<MenteeResponse> getMenteeList(final int mentorId) {
        memberRepository.findById(mentorId).orElseThrow(MemberNotFoundException::new);

        return memberRepository.findByMentorId(mentorId).stream()
                .map(MenteeResponse::of)
                .toList();
    }
}