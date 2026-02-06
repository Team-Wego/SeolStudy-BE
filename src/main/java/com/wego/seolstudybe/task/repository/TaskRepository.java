package com.wego.seolstudybe.task.repository;

import com.wego.seolstudybe.task.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;

public interface TaskRepository extends JpaRepository<Task, Integer> {

    @Query("""
        select coalesce(max(A.sequence), 0)
        from Task A
        where A.mentee.id = :menteeId
          and A.date = :date
    """)
    int findMaxSequenceByMenteeAndDate(
            @Param("menteeId") int menteeId,
            @Param("date") LocalDate date
    );

}
