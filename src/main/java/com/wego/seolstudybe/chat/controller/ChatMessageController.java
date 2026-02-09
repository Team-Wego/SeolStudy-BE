package com.wego.seolstudybe.chat.controller;

import com.wego.seolstudybe.chat.dto.ChatMessageRequestDTO;
import com.wego.seolstudybe.chat.dto.ChatMessageResponseDTO;
import com.wego.seolstudybe.chat.dto.ChatRoomResponseDTO;
import com.wego.seolstudybe.chat.service.ChatMessageService;
import com.wego.seolstudybe.chat.service.ChatRoomService;
import com.wego.seolstudybe.member.entity.Member;
import com.wego.seolstudybe.member.repository.MemberRepository;
import com.wego.seolstudybe.notification.entity.enums.NotificationType;
import com.wego.seolstudybe.notification.service.NotificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import java.util.Map;

@Slf4j
@Controller
@RequiredArgsConstructor
public class ChatMessageController {

    private final ChatMessageService chatMessageService;
    private final ChatRoomService chatRoomService;
    private final SimpMessagingTemplate messagingTemplate;
    private final NotificationService notificationService;
    private final MemberRepository memberRepository;

    /**
     * 메시지 전송
     * 클라이언트 -> /pub/chat/message
     * 서버 -> /sub/chat/room/{roomId} 로 브로드캐스트
     * 서버 -> /sub/user/{receiverId} 로 알림 전송
     */
    @MessageMapping("/chat/message")
    public void sendMessage(@Payload ChatMessageRequestDTO request) {
        log.info("메시지 수신: roomId={}, senderId={}, content={}",
                request.getRoomId(), request.getSenderId(), request.getContent());

        // 메시지 저장
        ChatMessageResponseDTO response = chatMessageService.saveMessage(request);

        // 해당 채팅방 구독자들에게 메시지 전송
        messagingTemplate.convertAndSend("/sub/chat/room/" + request.getRoomId(), response);

        // 상대방에게 알림 전송 (DB 저장 + FCM + WebSocket 통합)
        ChatRoomResponseDTO roomInfo = chatRoomService.getChatRoom(request.getRoomId());
        Long receiverId = request.getSenderId().equals(roomInfo.getMentorId())
                ? roomInfo.getMenteeId()
                : roomInfo.getMentorId();

        String senderName = memberRepository.findById(request.getSenderId().intValue())
                .map(Member::getName)
                .orElse("상대방");

        notificationService.notify(
                receiverId,
                NotificationType.CHAT,
                senderName + "님이 메시지를 보냈습니다",
                response.getContent() != null && !response.getContent().isBlank()
                        ? response.getContent() : "파일을 보냈습니다.",
                Map.of("type", "CHAT", "roomId", request.getRoomId(),
                        "senderId", String.valueOf(request.getSenderId()),
                        "senderName", senderName)
        );

        log.info("메시지 전송 완료: roomId={}, receiverId={}", request.getRoomId(), receiverId);
    }

    /**
     * 채팅방 입장 (자동 읽음 처리)
     * 클라이언트 -> /pub/chat/enter
     */
    @MessageMapping("/chat/enter")
    public void enterRoom(@Payload EnterRequest request) {
        log.info("채팅방 입장: roomId={}, userId={}", request.getRoomId(), request.getUserId());

        // 자동 읽음 처리
        chatMessageService.markMessagesAsRead(request.getRoomId(), request.getUserId());

        log.info("읽음 처리 완료: roomId={}, userId={}", request.getRoomId(), request.getUserId());
    }

    /**
     * 메시지 읽음 처리 (수동)
     * 클라이언트 -> /pub/chat/read
     */
    @MessageMapping("/chat/read")
    public void markAsRead(@Payload ReadRequest request) {
        log.info("읽음 처리: roomId={}, readerId={}", request.getRoomId(), request.getReaderId());

        chatMessageService.markMessagesAsRead(request.getRoomId(), request.getReaderId());
    }

    // 채팅방 입장용 DTO
    public static class EnterRequest {
        private String roomId;
        private Long userId;

        public String getRoomId() { return roomId; }
        public Long getUserId() { return userId; }
    }

    // 읽음 처리용 DTO
    public static class ReadRequest {
        private String roomId;
        private Long readerId;

        public String getRoomId() { return roomId; }
        public Long getReaderId() { return readerId; }
    }
}
