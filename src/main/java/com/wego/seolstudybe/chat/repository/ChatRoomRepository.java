package com.wego.seolstudybe.chat.repository;

import com.wego.seolstudybe.chat.entity.ChatRoom;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface ChatRoomRepository extends MongoRepository<ChatRoom, String> {

    Optional<ChatRoom> findByMentorIdAndMenteeId(Long mentorId, Long menteeId);

    List<ChatRoom> findByMentorIdOrderByLastMessageAtDesc(Long mentorId);

    List<ChatRoom> findByMenteeIdOrderByLastMessageAtDesc(Long menteeId);
}
