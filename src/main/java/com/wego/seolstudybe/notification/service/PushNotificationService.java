package com.wego.seolstudybe.notification.service;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import com.wego.seolstudybe.notification.dto.PushNotificationRequest;
import com.wego.seolstudybe.notification.entity.FcmToken;
import com.wego.seolstudybe.notification.repository.FcmTokenRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class PushNotificationService {

    private final FirebaseMessaging firebaseMessaging;
    private final FcmTokenRepository fcmTokenRepository;

    /**
     * FCM 토큰 등록/갱신
     */
    @Transactional
    public void registerToken(Long memberId, String token) {
        fcmTokenRepository.findByMemberId(memberId)
                .ifPresentOrElse(
                        existingToken -> existingToken.updateToken(token),
                        () -> fcmTokenRepository.save(FcmToken.builder()
                                .memberId(memberId)
                                .token(token)
                                .build())
                );
        log.info("FCM 토큰 등록: memberId={}", memberId);
    }

    /**
     * FCM 토큰 삭제 (로그아웃 시)
     */
    @Transactional
    public void removeToken(Long memberId) {
        fcmTokenRepository.deleteByMemberId(memberId);
        log.info("FCM 토큰 삭제: memberId={}", memberId);
    }

    /**
     * 단일 사용자에게 푸시 알림 전송
     */
    public void sendToUser(Long memberId, String title, String body) {
        fcmTokenRepository.findByMemberId(memberId)
                .ifPresent(fcmToken -> {
                    sendPush(fcmToken.getToken(), title, body, null);
                });
    }

    /**
     * 단일 사용자에게 푸시 알림 전송 (데이터 포함)
     */
    public void sendToUser(Long memberId, PushNotificationRequest request) {
        fcmTokenRepository.findByMemberId(memberId)
                .ifPresent(fcmToken -> {
                    sendPush(fcmToken.getToken(), request.getTitle(), request.getBody(), request.getData());
                });
    }

    /**
     * 여러 사용자에게 푸시 알림 전송
     */
    public void sendToUsers(List<Long> memberIds, String title, String body) {
        List<FcmToken> tokens = fcmTokenRepository.findAllByMemberIdIn(memberIds);
        tokens.forEach(fcmToken -> sendPush(fcmToken.getToken(), title, body, null));
    }

    /**
     * 실제 푸시 전송
     */
    private void sendPush(String token, String title, String body, java.util.Map<String, String> data) {
        try {
            Message.Builder messageBuilder = Message.builder()
                    .setToken(token)
                    .setNotification(Notification.builder()
                            .setTitle(title)
                            .setBody(body)
                            .build());

            if (data != null && !data.isEmpty()) {
                messageBuilder.putAllData(data);
            }

            String response = firebaseMessaging.send(messageBuilder.build());
            log.info("푸시 알림 전송 성공: {}", response);

        } catch (FirebaseMessagingException e) {
            log.error("푸시 알림 전송 실패: {}", e.getMessage());
        }
    }

}
