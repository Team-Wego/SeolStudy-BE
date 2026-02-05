package com.wego.seolstudybe.member.service;

import com.wego.seolstudybe.member.dto.LoginRequest;
import com.wego.seolstudybe.member.dto.LoginResponse;
import com.wego.seolstudybe.member.entity.Member;
import com.wego.seolstudybe.member.exception.InvalidPasswordException;
import com.wego.seolstudybe.member.exception.MemberNotFoundException;
import com.wego.seolstudybe.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    public LoginResponse login(LoginRequest request) {
        // 1. 이메일로 회원 조회
        Member member = memberRepository.findByEmail(request.getEmail())
                .orElseThrow(MemberNotFoundException::new);

        // 2. 비밀번호 확인
        if (!member.getPassword().equals(request.getPassword())) {
            throw new InvalidPasswordException();
        }

        // 3. 로그인 성공 - 응답 반환
        return new LoginResponse(member);
    }
}
