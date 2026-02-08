package com.wego.seolstudybe.member.controller;

import com.wego.seolstudybe.member.dto.LoginRequest;
import com.wego.seolstudybe.member.dto.LoginResponse;
import com.wego.seolstudybe.member.dto.MemberResponse;
import com.wego.seolstudybe.member.dto.MemoResponse;
import com.wego.seolstudybe.member.dto.UpdateMemberRequest;
import com.wego.seolstudybe.member.dto.UpdateMemoRequest;
import com.wego.seolstudybe.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/members")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {
        LoginResponse response = memberService.login(request);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MemberResponse> getMember(@PathVariable int id) {
        MemberResponse response = memberService.getMember(id);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<MemberResponse> updateMember(@PathVariable int id,
                                                       @RequestBody UpdateMemberRequest request) {
        MemberResponse response = memberService.updateMember(id, request);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{menteeId}/memo")
    public ResponseEntity<MemoResponse> getMemo(@PathVariable int menteeId) {
        MemoResponse response = memberService.getMemo(menteeId);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{menteeId}/memo")
    public ResponseEntity<MemoResponse> updateMemo(@PathVariable int menteeId,
                                                   @RequestBody UpdateMemoRequest request) {
        MemoResponse response = memberService.updateMemo(menteeId, request);
        return ResponseEntity.ok(response);
    }
}
