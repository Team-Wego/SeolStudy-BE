package com.wego.seolstudybe.member.dto;

import com.wego.seolstudybe.member.entity.enums.Grade;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UpdateMemberRequest {
    private String profileUrl;
    private String name;
    private Grade grade;
    private String goalUniversity;
}
