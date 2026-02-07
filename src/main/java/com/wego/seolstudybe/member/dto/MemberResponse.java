package com.wego.seolstudybe.member.dto;

import com.wego.seolstudybe.member.entity.Member;
import lombok.Getter;

@Getter
public class MemberResponse {
    private int id;
    private String email;
    private String name;
    private String role;
    private String profileUrl;
    private String grade;
    private String goalUniversity;
    private String memo;
    private String recentScore;

    public MemberResponse(Member member) {
        this.id = member.getId();
        this.email = member.getEmail();
        this.name = member.getName();
        this.role = member.getRole().name();
        this.profileUrl = member.getProfileUrl();
        this.grade = member.getGrade() != null ? member.getGrade().name() : null;
        this.goalUniversity = member.getGoalUniversity();
        this.memo = member.getMemo();
        this.recentScore = member.getRecentScore();
    }
}
