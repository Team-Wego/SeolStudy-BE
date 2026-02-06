package com.wego.seolstudybe.chat.controller;

import com.wego.seolstudybe.chat.dto.ChatRoomCreateRequestDTO;
import com.wego.seolstudybe.chat.dto.ChatRoomResponseDTO;
import com.wego.seolstudybe.chat.service.ChatRoomService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Chat Room", description = "채팅방 API")
@RestController
@RequestMapping("/chat/rooms")
@RequiredArgsConstructor
public class ChatRoomController {

    private final ChatRoomService chatRoomService;

    @Operation(summary = "채팅방 생성", description = "멘토-멘티 1:1 채팅방을 생성합니다. 이미 존재하면 기존 채팅방을 반환합니다.")
    @PostMapping
    public ResponseEntity<ChatRoomResponseDTO> createChatRoom(
            @Valid @RequestBody ChatRoomCreateRequestDTO request) {
        ChatRoomResponseDTO response = chatRoomService.createOrGetChatRoom(
                request.getMentorId(),
                request.getMenteeId()
        );
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "채팅방 단건 조회", description = "채팅방 ID로 채팅방 정보를 조회합니다.")
    @GetMapping("/{roomId}")
    public ResponseEntity<ChatRoomResponseDTO> getChatRoom(
            @Parameter(description = "채팅방 ID") @PathVariable String roomId) {
        return ResponseEntity.ok(chatRoomService.getChatRoom(roomId));
    }

    @Operation(summary = "멘토 채팅방 목록 조회", description = "멘토의 모든 채팅방 목록을 조회합니다.")
    @GetMapping("/mentor/{mentorId}")
    public ResponseEntity<List<ChatRoomResponseDTO>> getMentorChatRooms(
            @Parameter(description = "멘토 ID (테스트: 1)") @PathVariable Long mentorId) {
        return ResponseEntity.ok(chatRoomService.getMentorChatRooms(mentorId));
    }

    @Operation(summary = "멘티 채팅방 목록 조회", description = "멘티의 모든 채팅방 목록을 조회합니다.")
    @GetMapping("/mentee/{menteeId}")
    public ResponseEntity<List<ChatRoomResponseDTO>> getMenteeChatRooms(
            @Parameter(description = "멘티 ID (테스트: 2 또는 3)") @PathVariable Long menteeId) {
        return ResponseEntity.ok(chatRoomService.getMenteeChatRooms(menteeId));
    }

    @Operation(summary = "기존 채팅방 조회", description = "멘토-멘티 간 기존 채팅방이 있는지 확인합니다.")
    @GetMapping("/check")
    public ResponseEntity<ChatRoomResponseDTO> checkExistingChatRoom(
            @Parameter(description = "멘토 ID") @RequestParam Long mentorId,
            @Parameter(description = "멘티 ID") @RequestParam Long menteeId) {
        return chatRoomService.findExistingChatRoom(mentorId, menteeId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
