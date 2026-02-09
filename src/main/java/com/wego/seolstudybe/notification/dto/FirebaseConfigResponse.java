package com.wego.seolstudybe.notification.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class FirebaseConfigResponse {

    private String apiKey;
    private String authDomain;
    private String projectId;
    private String storageBucket;
    private String messagingSenderId;
    private String appId;
    private String vapidKey;
}
