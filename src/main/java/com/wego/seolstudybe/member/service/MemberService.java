package com.wego.seolstudybe.member.service;

import com.wego.seolstudybe.common.service.S3Service;
import com.wego.seolstudybe.member.dto.LoginRequest;
import com.wego.seolstudybe.member.dto.LoginResponse;
import com.wego.seolstudybe.member.dto.MemberResponse;
import com.wego.seolstudybe.member.dto.MemoResponse;
import com.wego.seolstudybe.member.dto.UpdateMemberRequest;
import com.wego.seolstudybe.member.dto.UpdateMemoRequest;
import com.wego.seolstudybe.member.entity.Member;
import com.wego.seolstudybe.member.entity.enums.Role;
import com.wego.seolstudybe.member.exception.InvalidPasswordException;
import com.wego.seolstudybe.member.exception.MemberNotFoundException;
import com.wego.seolstudybe.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class MemberService {
    private static final String PROFILE_FOLDER = "profile";

    private final MemberRepository memberRepository;
    private final S3Service s3Service;

    @Transactional(readOnly = true)
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

    @Transactional(readOnly = true)
    public MemberResponse getMember(int id) {
        Member member = memberRepository.findById(id)
                .orElseThrow(MemberNotFoundException::new);
        return new MemberResponse(member);
    }

    @Transactional
    public MemberResponse updateMember(final int id, final MultipartFile file, final UpdateMemberRequest request) {
        final Member member = memberRepository.findById(id)
                .orElseThrow(MemberNotFoundException::new);

        String profileUrl = request.getProfileUrl();

        if (request.isNewProfileImage()) {
            if (profileUrl != null) {
                s3Service.deleteFile(profileUrl);
            }

            if (file != null && !file.isEmpty()) {
                profileUrl = s3Service.uploadFile(file, PROFILE_FOLDER);
            } else {
                profileUrl = null;
            }
        }

        member.update(profileUrl, request.getName(), request.getGrade(), request.getGoalUniversity());

        return new MemberResponse(member);
    }

    @Transactional(readOnly = true)
    public MemoResponse getMemo(int menteeId) {
        Member member = memberRepository.findById(menteeId)
                .orElseThrow(MemberNotFoundException::new);
        return new MemoResponse(member);
    }

    @Transactional
    public MemoResponse updateMemo(int menteeId, UpdateMemoRequest request) {
        Member member = memberRepository.findById(menteeId)
                .orElseThrow(MemberNotFoundException::new);

        member.updateMemo(request.getMemo());

        return new MemoResponse(member);
    }
}