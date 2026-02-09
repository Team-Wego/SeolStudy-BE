package com.wego.seolstudybe.chat.repository;

import com.wego.seolstudybe.chat.entity.ChatMessage;
import com.wego.seolstudybe.chat.entity.enums.MessageType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ChatMessageRepository extends MongoRepository<ChatMessage, String> {

    Page<ChatMessage> findByRoomIdOrderBySentAtDesc(String roomId, Pageable pageable);

    List<ChatMessage> findByRoomIdAndIsReadFalseAndSenderIdNot(String roomId, Long readerId);

    long countByRoomIdAndIsReadFalseAndSenderIdNot(String roomId, Long readerId);

    // 미디어/파일 조회 (특정 타입)
    List<ChatMessage> findByRoomIdAndMessageTypeInOrderBySentAtDesc(String roomId, List<MessageType> types);

    // 특정 타입의 미디어 조회
    List<ChatMessage> findByRoomIdAndMessageTypeOrderBySentAtDesc(String roomId, MessageType type);
}
