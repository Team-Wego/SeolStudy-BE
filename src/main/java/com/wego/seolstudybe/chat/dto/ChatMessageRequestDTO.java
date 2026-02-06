package com.wego.seolstudybe.chat.dto;

import com.wego.seolstudybe.chat.entity.enums.MessageType;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ChatMessageRequestDTO {

    private String roomId;
    private Long senderId;
    private MessageType messageType;
    private String content;
    private String fileUrl;
    private String fileName;
}
