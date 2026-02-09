package com.wego.seolstudybe.task.service;

import com.wego.seolstudybe.common.error.ErrorCode;
import com.wego.seolstudybe.common.error.exception.BusinessException;
import com.wego.seolstudybe.member.entity.Member;
import com.wego.seolstudybe.member.exception.MemberNotFoundException;
import com.wego.seolstudybe.member.repository.MemberRepository;
import com.wego.seolstudybe.notification.entity.enums.NotificationType;
import com.wego.seolstudybe.notification.service.NotificationService;
import com.wego.seolstudybe.task.dto.request.PlannerCommentCreateRequest;
import com.wego.seolstudybe.task.dto.request.PlannerCommentUpdateRequest;
import com.wego.seolstudybe.task.dto.response.PlannerCommentResponse;
import com.wego.seolstudybe.task.entity.Planner;
import com.wego.seolstudybe.task.exception.PlannerNotFoundException;
import com.wego.seolstudybe.task.repository.PlannerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PlannerCommentServiceImpl implements PlannerCommentService {

    private final PlannerRepository plannerRepository;
    private final MemberRepository memberRepository;
    private final NotificationService notificationService;

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

    @Override
    @Transactional
    public PlannerCommentResponse completePlanner(int menteeId, int plannerId) {
        Planner planner = plannerRepository.findByIdAndMenteeId(plannerId, menteeId)
                .orElseThrow(PlannerNotFoundException::new);

        if (planner.isCompleted()) {
            throw new BusinessException(ErrorCode.PLANNER_ALREADY_COMPLETED);
        }

        planner.complete();

        // 담당 멘토에게 알림 전송
        Member mentee = planner.getMentee();
        if (mentee.getMentor() != null) {
            Long mentorId = (long) mentee.getMentor().getId();
            String title = mentee.getName() + "님이 플래너를 마감했습니다";
            String body = planner.getDate() + " 플래너가 마감되었습니다.";

            notificationService.notify(
                    mentorId,
                    NotificationType.PLANNER_COMPLETED,
                    title,
                    body,
                    Map.of(
                            "type", "PLANNER_COMPLETED",
                            "menteeId", String.valueOf(menteeId),
                            "plannerId", String.valueOf(plannerId),
                            "date", planner.getDate().toString()
                    )
            );
        }

        return PlannerCommentResponse.from(planner);
    }
}
