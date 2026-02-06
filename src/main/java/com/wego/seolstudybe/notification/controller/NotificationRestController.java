package com.wego.seolstudybe.notification.controller;

import com.wego.seolstudybe.notification.dto.NotificationResponseDTO;
import com.wego.seolstudybe.notification.service.NotificationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Tag(name = "Notification", description = "알림 API")
@RestController
@RequestMapping("/notification")
@RequiredArgsConstructor
public class NotificationRestController {

    private final NotificationService notificationService;

    @Operation(summary = "알림 목록 조회", description = "사용자의 알림 목록을 페이징하여 조회합니다.")
    @GetMapping("/{memberId:\\d+}")
    public ResponseEntity<Page<NotificationResponseDTO>> getNotifications(
            @PathVariable Long memberId,
            @PageableDefault(size = 20) Pageable pageable) {
        return ResponseEntity.ok(notificationService.getNotifications(memberId, pageable));
    }

    @Operation(summary = "알림 읽음 처리", description = "알림을 읽음 상태로 변경합니다.")
    @PatchMapping("/{notificationId}/read")
    public ResponseEntity<Void> markAsRead(@PathVariable Long notificationId) {
        notificationService.markAsRead(notificationId);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "미읽은 알림 수 조회", description = "미읽은 알림 수를 조회합니다.")
    @GetMapping("/{memberId:\\d+}/unread-count")
    public ResponseEntity<Map<String, Long>> getUnreadCount(@PathVariable Long memberId) {
        long count = notificationService.getUnreadCount(memberId);
        return ResponseEntity.ok(Map.of("count", count));
    }
}
