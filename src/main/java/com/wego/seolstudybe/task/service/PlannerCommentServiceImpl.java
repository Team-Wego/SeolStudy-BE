package com.wego.seolstudybe.task.service;

import com.wego.seolstudybe.member.entity.Member;
import com.wego.seolstudybe.member.exception.MemberNotFoundException;
import com.wego.seolstudybe.member.repository.MemberRepository;
import com.wego.seolstudybe.task.dto.request.PlannerCommentCreateRequest;
import com.wego.seolstudybe.task.dto.request.PlannerCommentUpdateRequest;
import com.wego.seolstudybe.task.dto.response.PlannerCommentResponse;
import com.wego.seolstudybe.task.entity.Planner;
import com.wego.seolstudybe.task.exception.PlannerNotFoundException;
import com.wego.seolstudybe.task.repository.PlannerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PlannerCommentServiceImpl implements PlannerCommentService {

    private final PlannerRepository plannerRepository;
    private final MemberRepository memberRepository;

    @Override
    @Transactional
    public PlannerCommentResponse createPlannerComment(int menteeId, PlannerCommentCreateRequest request) {
        Member mentee = memberRepository.findById(menteeId)
                .orElseThrow(MemberNotFoundException::new);

        Planner planner = plannerRepository.findByMenteeIdAndDate(menteeId, request.getDate())
                .orElseGet(() -> Planner.builder()
                        .mentee(mentee)
                        .date(request.getDate())
                        .comment(request.getComment())
                        .build());

        planner.updateComment(request.getComment());

        Planner savedPlanner = plannerRepository.save(planner);
        return PlannerCommentResponse.from(savedPlanner);
    }

    @Override
    @Transactional
    public PlannerCommentResponse updatePlannerComment(int menteeId, int plannerId, PlannerCommentUpdateRequest request) {
        Planner planner = plannerRepository.findByIdAndMenteeId(plannerId, menteeId)
                .orElseThrow(PlannerNotFoundException::new);

        planner.updateComment(request.getComment());
        return PlannerCommentResponse.from(planner);
    }

    @Override
    @Transactional
    public void deletePlannerComment(int menteeId, int plannerId) {
        Planner planner = plannerRepository.findByIdAndMenteeId(plannerId, menteeId)
                .orElseThrow(PlannerNotFoundException::new);

        planner.updateComment("");
    }
}
