package com.wego.seolstudybe.mentoring.repository;

import com.wego.seolstudybe.mentoring.entity.Feedback;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FeedbackRepository extends JpaRepository<Feedback, Integer> {
}