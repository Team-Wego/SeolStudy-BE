package com.wego.seolstudybe.task.repository;

import com.wego.seolstudybe.task.entity.Task;
import com.wego.seolstudybe.task.entity.enums.TaskType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface TaskRepository extends JpaRepository<Task, Integer> {

    Optional<Task> findByIdAndMenteeId(int id, int menteeId);

    List<Task> findByMenteeIdAndDateAndTypeOrderBySequenceAsc(int menteeId, LocalDate date, TaskType type);

    @Query("SELECT COALESCE(MAX(t.sequence), 0) FROM Task t WHERE t.mentee.id = :menteeId AND t.date = :date")
    int findMaxSequenceByMenteeIdAndDate(@Param("menteeId") int menteeId, @Param("date") LocalDate date);

    List<Task> findByMenteeIdAndDateOrderBySequenceAsc(int menteeId, LocalDate date);

    List<Task> findByDateAndIsCheckedFalse(LocalDate date);
}
