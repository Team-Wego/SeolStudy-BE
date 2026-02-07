package com.wego.seolstudybe.task.repository;

import com.wego.seolstudybe.task.entity.Planner;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.Optional;

public interface PlannerRepository extends JpaRepository<Planner, Integer> {

    Optional<Planner> findByMenteeIdAndDate(int menteeId, LocalDate date);

    Optional<Planner> findByIdAndMenteeId(int id, int menteeId);
}
