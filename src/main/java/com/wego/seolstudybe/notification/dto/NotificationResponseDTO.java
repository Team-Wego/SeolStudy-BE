package com.wego.seolstudybe.notification.dto;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wego.seolstudybe.notification.entity.Notification;
import com.wego.seolstudybe.notification.entity.enums.NotificationType;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.Map;

@Getter
@Builder
public class NotificationResponseDTO {

    private Long id;
    private NotificationType type;
    private String title;
    private String body;
    private boolean isRead;
    private Map<String, String> data;
    private LocalDateTime createdAt;

    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static NotificationResponseDTO from(Notification notification) {
        Map<String, String> dataMap = null;
        if (notification.getData() != null) {
            try {
                dataMap = objectMapper.readValue(notification.getData(), new TypeReference<>() {});
            } catch (Exception e) {
                dataMap = null;
            }
        }

        return NotificationResponseDTO.builder()
                .id(notification.getId())
                .type(notification.getType())
                .title(notification.getTitle())
                .body(notification.getBody())
                .isRead(notification.isRead())
                .data(dataMap)
                .createdAt(notification.getCreatedAt())
                .build();
    }
}
