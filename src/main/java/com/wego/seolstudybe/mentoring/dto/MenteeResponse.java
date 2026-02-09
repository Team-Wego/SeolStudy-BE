package com.wego.seolstudybe.mentoring.dto;

import com.wego.seolstudybe.member.entity.Member;
import com.wego.seolstudybe.member.entity.enums.Grade;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Builder
@Getter
public class MenteeResponse {
    @Schema(description = "멘티 ID(PK)")
    private int menteeId;

    @Schema(description = "멘티 이름")
    private String name;

    @Schema(description = "멘티 학년")
    private Grade grade;

    @Schema(description = "멘티 프로필 이미지")
    private String profileUrl;

    @Schema(description = "멘티 담당 시작일")
    private LocalDateTime startedAt;

    @Schema(description = "멘티 담당 종료일")
    private LocalDateTime endedAt;

    @Schema(description = "멘티 모의고사 성적(국영수)")
    private String recentScore;

    @Schema(description = "멘티 목표 대학")
    private String goalUniversity;

    public static MenteeResponse of(Member member) {
        return MenteeResponse.builder()
                .menteeId(member.getId())
                .name(member.getName())
                .grade(member.getGrade())
                .profileUrl(member.getProfileUrl())
                .startedAt(member.getStartedAt())
                .endedAt(member.getEndedAt())
                .recentScore(member.getRecentScore())
                .goalUniversity(member.getGoalUniversity())
                .build();
    }
}