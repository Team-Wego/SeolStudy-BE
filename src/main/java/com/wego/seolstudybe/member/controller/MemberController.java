package com.wego.seolstudybe.member.controller;

import com.wego.seolstudybe.member.dto.LoginRequest;
import com.wego.seolstudybe.member.dto.LoginResponse;
import com.wego.seolstudybe.member.dto.MemberResponse;
import com.wego.seolstudybe.member.dto.UpdateMemberRequest;
import com.wego.seolstudybe.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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

    @PutMapping(consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<MemberResponse> updateMember(@CookieValue("memberId") final int memberId,
                                                       @RequestPart(value = "file", required = false) final MultipartFile file,
                                                       @RequestPart("request") UpdateMemberRequest request) {
        MemberResponse response = memberService.updateMember(memberId, file, request);

        return ResponseEntity.ok(response);
    }
}
