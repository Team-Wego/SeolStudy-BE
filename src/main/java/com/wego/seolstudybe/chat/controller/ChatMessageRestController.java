package com.wego.seolstudybe.chat.controller;

import com.wego.seolstudybe.chat.dto.ChatMessageResponse;
import com.wego.seolstudybe.chat.service.ChatMessageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Chat Message", description = "채팅 메시지 API")
@RestController
@RequestMapping("/chat/messages")
@RequiredArgsConstructor
public class ChatMessageRestController {

    private final ChatMessageService chatMessageService;

    @Operation(summary = "메시지 이력 조회", description = "채팅방의 메시지 이력을 조회합니다 (최신순)")
    @GetMapping("/{roomId}")
    public ResponseEntity<List<ChatMessageResponse>> getMessages(
            @Parameter(description = "채팅방 ID") @PathVariable String roomId,
            @Parameter(description = "페이지 번호 (0부터 시작)") @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "페이지 크기") @RequestParam(defaultValue = "50") int size) {
        return ResponseEntity.ok(chatMessageService.getMessages(roomId, page, size));
    }

    @Operation(summary = "읽지 않은 메시지 수 조회")
    @GetMapping("/{roomId}/unread")
    public ResponseEntity<Long> getUnreadCount(
            @Parameter(description = "채팅방 ID") @PathVariable String roomId,
            @Parameter(description = "사용자 ID") @RequestParam Long userId) {
        return ResponseEntity.ok(chatMessageService.getUnreadCount(roomId, userId));
    }

    @Operation(summary = "메시지 읽음 처리")
    @PostMapping("/{roomId}/read")
    public ResponseEntity<Void> markAsRead(
            @Parameter(description = "채팅방 ID") @PathVariable String roomId,
            @Parameter(description = "읽은 사용자 ID") @RequestParam Long readerId) {
        chatMessageService.markMessagesAsRead(roomId, readerId);
        return ResponseEntity.ok().build();
    }
}
