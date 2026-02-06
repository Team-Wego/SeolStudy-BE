package com.wego.seolstudybe.member.dto;

import com.wego.seolstudybe.member.entity.Member;
import lombok.Getter;

@Getter
public class LoginResponse {
    private int id;
    private String email;
    private String name;
    private String role;
    private String profileUrl;

    public LoginResponse(Member member) {
        this.id = member.getId();
        this.email = member.getEmail();
        this.name = member.getName();
        this.role = member.getRole().name();
        this.profileUrl = member.getProfileUrl();
    }
}
