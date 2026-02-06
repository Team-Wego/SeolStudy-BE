package com.wego.seolstudybe.notification.repository;

import com.wego.seolstudybe.notification.entity.FcmToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FcmTokenRepository extends JpaRepository<FcmToken, Long> {

    Optional<FcmToken> findByMemberId(Long memberId);

    List<FcmToken> findAllByMemberIdIn(List<Long> memberIds);

    void deleteByMemberId(Long memberId);
}
