package com.wego.seolstudybe.notification.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Map;

@Getter
@NoArgsConstructor
public class PushNotificationRequest {

    private String title;
    private String body;
    private Map<String, String> data;

    @Builder
    public PushNotificationRequest(String title, String body, Map<String, String> data) {
        this.title = title;
        this.body = body;
        this.data = data;
    }
}
