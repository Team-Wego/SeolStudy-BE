package com.wego.seolstudybe.notification.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wego.seolstudybe.common.error.ErrorCode;
import com.wego.seolstudybe.common.error.exception.BusinessException;
import com.wego.seolstudybe.notification.dto.NotificationResponseDTO;
import com.wego.seolstudybe.notification.entity.Notification;
import com.wego.seolstudybe.notification.entity.enums.NotificationType;
import com.wego.seolstudybe.notification.repository.NotificationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class NotificationService {

    private final NotificationRepository notificationRepository;
    private final PushNotificationService pushNotificationService;
    private final SimpMessagingTemplate messagingTemplate;
    private static final ObjectMapper objectMapper = new ObjectMapper();

    /**
     * 알림 전송 (DB 저장 + FCM 푸시 + WebSocket)
     */
    @Transactional
    public void notify(Long receiverId, NotificationType type, String title, String body, Map<String, String> data) {
        // 1. DB 저장
        String dataJson = null;
        if (data != null && !data.isEmpty()) {
            try {
                dataJson = objectMapper.writeValueAsString(data);
            } catch (Exception e) {
                log.warn("알림 데이터 직렬화 실패: {}", e.getMessage());
            }
        }

        Notification notification = Notification.builder()
                .type(type)
                .title(title)
                .body(body)
                .receiverId(receiverId)
                .data(dataJson)
                .build();
        notificationRepository.save(notification);

        NotificationResponseDTO responseDTO = NotificationResponseDTO.from(notification);

        // 2. WebSocket 실시간 알림
        messagingTemplate.convertAndSend("/sub/user/" + receiverId + "/notification", responseDTO);

        // 3. FCM 푸시 알림
        pushNotificationService.sendToUser(receiverId, title, body);

        log.info("알림 전송 완료: receiverId={}, type={}, title={}", receiverId, type, title);
    }

    /**
     * 알림 목록 조회
     */
    @Transactional(readOnly = true)
    public Page<NotificationResponseDTO> getNotifications(Long receiverId, Pageable pageable) {
        return notificationRepository.findByReceiverIdOrderByCreatedAtDesc(receiverId, pageable)
                .map(NotificationResponseDTO::from);
    }

    /**
     * 알림 읽음 처리
     */
    @Transactional
    public void markAsRead(Long notificationId) {
        Notification notification = notificationRepository.findById(notificationId)
                .orElseThrow(() -> new BusinessException(ErrorCode.NOTIFICATION_NOT_FOUND));
        notification.markAsRead();
    }

    /**
     * 읽지 않은 알림 수 조회
     */
    @Transactional(readOnly = true)
    public long getUnreadCount(Long receiverId) {
        return notificationRepository.countByReceiverIdAndIsReadFalse(receiverId);
    }
}
