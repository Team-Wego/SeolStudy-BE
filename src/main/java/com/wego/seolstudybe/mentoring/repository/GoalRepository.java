package com.wego.seolstudybe.mentoring.repository;

import com.wego.seolstudybe.mentoring.entity.Goal;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GoalRepository extends JpaRepository<Goal, Integer> {
}
