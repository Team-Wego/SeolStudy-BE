package com.wego.seolstudybe.mentoring.repository;

import com.wego.seolstudybe.mentoring.dto.DailyFeedbackCountResponse;
import com.wego.seolstudybe.mentoring.entity.Feedback;
import com.wego.seolstudybe.mentoring.entity.enums.FeedbackType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface FeedbackRepository extends JpaRepository<Feedback, Integer> {
    @Query("SELECT f FROM Feedback f LEFT JOIN FETCH f.mentor LEFT JOIN FETCH f.mentee LEFT JOIN FETCH f.task " +
            "WHERE f.task.id = :taskId AND f.type = 'TASK'")
    Optional<Feedback> findByTaskIdWithDetails(@Param("taskId") int taskId);

    @Query("SELECT f FROM Feedback f LEFT JOIN FETCH f.task t LEFT JOIN FETCH t.goal " +
            "WHERE f.mentee.id = :menteeId ORDER BY f.createdAt DESC")
    List<Feedback> findByMenteeIdOrderByCreatedAtDesc(@Param("menteeId") final int menteeId);

    @Query("SELECT f FROM Feedback f LEFT JOIN FETCH f.task t LEFT JOIN FETCH t.goal " +
            "WHERE f.mentee.id = :menteeId AND f.type = :type ORDER BY f.createdAt DESC")
    List<Feedback> findByMenteeIdAndTypeOrderByCreatedAtDesc(
            @Param("menteeId") final int menteeId, @Param("type") final FeedbackType type);

    @Query("SELECT f.targetDate AS date, COUNT(f) AS count FROM Feedback f " +
            "WHERE f.mentee.id = :menteeId AND f.targetDate BETWEEN :startDate AND :endDate AND f.type = 'TASK'" +
            "GROUP BY f.targetDate ORDER BY f.targetDate")
    List<DailyFeedbackCountResponse> countDailyByMenteeIdAndTargetDateBetween(
            @Param("menteeId") final int menteeId,
            @Param("startDate") final LocalDate startDate,
            @Param("endDate") final LocalDate endDate);

    Feedback findByTargetDateAndMenteeIdAndType(final LocalDate targetDate, final int menteeId, final FeedbackType type);

    boolean existsByMenteeIdAndTargetDateAndType(final int menteeId, final LocalDate targetDate, final FeedbackType type);

    boolean existsByMenteeIdAndTargetDateAndTypeAndTaskId(final int menteeId, final LocalDate targetDate, final FeedbackType type, final int taskId);
}