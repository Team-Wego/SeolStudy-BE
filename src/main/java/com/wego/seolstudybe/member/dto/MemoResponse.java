package com.wego.seolstudybe.member.dto;

import com.wego.seolstudybe.member.entity.Member;
import lombok.Getter;

@Getter
public class MemoResponse {
    private int menteeId;
    private String name;
    private String memo;

    public MemoResponse(Member member) {
        this.menteeId = member.getId();
        this.name = member.getName();
        this.memo = member.getMemo();
    }
}
