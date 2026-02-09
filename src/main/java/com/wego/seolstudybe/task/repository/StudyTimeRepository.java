package com.wego.seolstudybe.task.repository;

import com.wego.seolstudybe.task.entity.StudyTime;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StudyTimeRepository extends JpaRepository<StudyTime, Integer> {

    Optional<StudyTime> findByIdAndTaskMenteeId(int id, int menteeId);
}
