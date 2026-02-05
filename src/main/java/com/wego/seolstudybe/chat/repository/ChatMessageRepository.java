package com.wego.seolstudybe.chat.repository;

import com.wego.seolstudybe.chat.entity.ChatMessage;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ChatMessageRepository extends MongoRepository<ChatMessage, String> {

    Page<ChatMessage> findByRoomIdOrderBySentAtDesc(String roomId, Pageable pageable);

    List<ChatMessage> findByRoomIdAndIsReadFalseAndSenderIdNot(String roomId, Long readerId);

    long countByRoomIdAndIsReadFalseAndSenderIdNot(String roomId, Long readerId);
}
