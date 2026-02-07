package com.wego.seolstudybe.mentoring.repository;

import com.wego.seolstudybe.mentoring.dto.DailyFeedbackCountResponse;
import com.wego.seolstudybe.mentoring.entity.Feedback;
import com.wego.seolstudybe.mentoring.entity.enums.FeedbackType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface FeedbackRepository extends JpaRepository<Feedback, Integer> {
    List<Feedback> findByMenteeIdOrderByCreatedAtDesc(final int menteeId);

    List<Feedback> findByMenteeIdAndTypeOrderByCreatedAtDesc(final int menteeId, final FeedbackType type);

    @Query("SELECT f.targetDate AS date, COUNT(f) AS count FROM Feedback f " +
            "WHERE f.mentee.id = :menteeId AND f.targetDate BETWEEN :startDate AND :endDate AND f.type = 'TASK'" +
            "GROUP BY f.targetDate ORDER BY f.targetDate")
    List<DailyFeedbackCountResponse> countDailyByMenteeIdAndTargetDateBetween(
            @Param("menteeId") final int menteeId,
            @Param("startDate") final LocalDate startDate,
            @Param("endDate") final LocalDate endDate);

    Feedback findByTargetDateAndMenteeId(final LocalDate targetDate, final int menteeId);

    boolean existsByMenteeIdAndTargetDateAndType(final int menteeId, final LocalDate targetDate, final FeedbackType type);
}