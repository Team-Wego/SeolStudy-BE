package com.wego.seolstudybe.mentoring.repository;

import com.wego.seolstudybe.member.entity.Member;
import com.wego.seolstudybe.mentoring.entity.Goal;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GoalRepository extends JpaRepository<Goal, Integer> {
    Boolean existsByNameAndCreatorAndTargetMentee(final String name, final Member creator, final Member targetMentee);
}