package com.wego.seolstudybe.notification.controller;

import com.wego.seolstudybe.notification.config.FirebaseWebProperties;
import com.wego.seolstudybe.notification.dto.FcmTokenRequest;
import com.wego.seolstudybe.notification.dto.FirebaseConfigResponse;
import com.wego.seolstudybe.notification.service.PushNotificationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Notification", description = "푸시 알림 API")
@RestController
@RequestMapping("/notification")
@RequiredArgsConstructor
public class NotificationController {

    private final PushNotificationService pushNotificationService;
    private final FirebaseWebProperties firebaseWebProperties;

    @Operation(summary = "FCM 토큰 등록", description = "푸시 알림을 받기 위한 FCM 토큰을 등록합니다.")
    @PostMapping("/token")
    public ResponseEntity<Void> registerToken(@Valid @RequestBody FcmTokenRequest request) {
        pushNotificationService.registerToken(request.getMemberId(), request.getToken());
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "FCM 토큰 삭제", description = "로그아웃 시 FCM 토큰을 삭제합니다.")
    @DeleteMapping("/token/{memberId}")
    public ResponseEntity<Void> removeToken(@PathVariable Long memberId) {
        pushNotificationService.removeToken(memberId);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "테스트 푸시 전송", description = "테스트용 푸시 알림을 전송합니다.")
    @PostMapping("/test/{memberId}")
    public ResponseEntity<Void> sendTestPush(@PathVariable Long memberId) {
        pushNotificationService.sendToUser(memberId, "테스트 알림", "푸시 알림이 정상적으로 동작합니다!");
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Firebase 웹 설정 조회", description = "클라이언트용 Firebase 설정을 반환합니다.")
    @GetMapping("/firebase-config")
    public ResponseEntity<FirebaseConfigResponse> getFirebaseConfig() {
        FirebaseConfigResponse config = FirebaseConfigResponse.builder()
                .apiKey(firebaseWebProperties.getApiKey())
                .authDomain(firebaseWebProperties.getAuthDomain())
                .projectId(firebaseWebProperties.getProjectId())
                .storageBucket(firebaseWebProperties.getStorageBucket())
                .messagingSenderId(firebaseWebProperties.getMessagingSenderId())
                .appId(firebaseWebProperties.getAppId())
                .vapidKey(firebaseWebProperties.getVapidKey())
                .build();
        return ResponseEntity.ok(config);
    }
}
