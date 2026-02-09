package com.wego.seolstudybe.task.dto.response;

import com.wego.seolstudybe.task.entity.Planner;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Builder
public class PlannerCommentResponse {

    private final int id;
    private final int menteeId;
    private final LocalDate date;
    private final String comment;
    private final LocalDateTime completedAt;

    public static PlannerCommentResponse from(Planner planner) {
        return PlannerCommentResponse.builder()
                .id(planner.getId())
                .menteeId(planner.getMentee().getId())
                .date(planner.getDate())
                .comment(planner.getComment())
                .completedAt(planner.getCompletedAt())
                .build();
    }
}
