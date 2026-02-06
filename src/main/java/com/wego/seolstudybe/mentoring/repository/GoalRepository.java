package com.wego.seolstudybe.mentoring.repository;

import com.wego.seolstudybe.mentoring.entity.Goal;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface GoalRepository extends JpaRepository<Goal, Integer> {
    Optional<Goal> findByIdAndDeletedAtIsNull(final int id);
}