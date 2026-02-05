package com.wego.seolstudybe.chat.controller;

import com.wego.seolstudybe.chat.dto.ChatMessageRequest;
import com.wego.seolstudybe.chat.dto.ChatMessageResponse;
import com.wego.seolstudybe.chat.service.ChatMessageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Slf4j
@Controller
@RequiredArgsConstructor
public class ChatMessageController {

    private final ChatMessageService chatMessageService;
    private final SimpMessagingTemplate messagingTemplate;

    /**
     * 메시지 전송
     * 클라이언트 -> /pub/chat/message
     * 서버 -> /sub/chat/room/{roomId} 로 브로드캐스트
     */
    @MessageMapping("/chat/message")
    public void sendMessage(@Payload ChatMessageRequest request) {
        log.info("메시지 수신: roomId={}, senderId={}, content={}",
                request.getRoomId(), request.getSenderId(), request.getContent());

        // 메시지 저장
        ChatMessageResponse response = chatMessageService.saveMessage(request);

        // 해당 채팅방 구독자들에게 메시지 전송
        messagingTemplate.convertAndSend("/sub/chat/room/" + request.getRoomId(), response);

        log.info("메시지 전송 완료: roomId={}", request.getRoomId());
    }

    /**
     * 메시지 읽음 처리
     * 클라이언트 -> /pub/chat/read
     */
    @MessageMapping("/chat/read")
    public void markAsRead(@Payload ReadRequest request) {
        log.info("읽음 처리: roomId={}, readerId={}", request.getRoomId(), request.getReaderId());

        chatMessageService.markMessagesAsRead(request.getRoomId(), request.getReaderId());
    }

    // 읽음 처리용 내부 클래스
    public static class ReadRequest {
        private String roomId;
        private Long readerId;

        public String getRoomId() { return roomId; }
        public Long getReaderId() { return readerId; }
    }
}
