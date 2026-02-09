package com.wego.seolstudybe.mentoring.repository;

import com.wego.seolstudybe.mentoring.entity.Goal;
import com.wego.seolstudybe.mentoring.entity.WorksheetFile;
import com.wego.seolstudybe.mentoring.entity.enums.Subject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface GoalRepository extends JpaRepository<Goal, Integer> {
    Optional<Goal> findByIdAndDeletedAtIsNull(final int id);

    @Query("select b from Goal a join WorksheetFile b on a.worksheetFile.id = b.id " +
            "where a.targetMentee.id = :menteeId and a.subject = :subject and a.deletedAt is null")
    List<WorksheetFile> findWorksheetFileByMenteeIdAndSubject(final int menteeId, final Subject subject);

    @Query("select b from Goal a join WorksheetFile b on a.worksheetFile.id = b.id " +
            "where a.targetMentee.id = :menteeId and a.deletedAt is null")
    List<WorksheetFile> findWorksheetFileByMenteeId(final int menteeId);
}