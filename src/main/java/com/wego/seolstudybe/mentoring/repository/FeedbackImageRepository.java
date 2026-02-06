package com.wego.seolstudybe.mentoring.repository;

import com.wego.seolstudybe.mentoring.entity.FeedbackImage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FeedbackImageRepository extends JpaRepository<FeedbackImage, Integer> {
    List<FeedbackImage> findByFeedbackId(final int feedbackId);
}