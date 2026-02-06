package com.wego.seolstudybe.mentoring.service;

import com.wego.seolstudybe.member.entity.Member;
import com.wego.seolstudybe.member.exception.MemberNotFoundException;
import com.wego.seolstudybe.member.repository.MemberRepository;
import com.wego.seolstudybe.mentoring.dao.GoalMapper;
import com.wego.seolstudybe.mentoring.dto.CreateGoalRequest;
import com.wego.seolstudybe.mentoring.dto.GoalResponse;
import com.wego.seolstudybe.mentoring.dto.UpdateGoalRequest;
import com.wego.seolstudybe.mentoring.entity.Goal;
import com.wego.seolstudybe.mentoring.entity.WorksheetFile;
import com.wego.seolstudybe.mentoring.entity.enums.GoalCreator;
import com.wego.seolstudybe.mentoring.entity.enums.Subject;
import com.wego.seolstudybe.mentoring.exception.GoalAccessDeniedException;
import com.wego.seolstudybe.mentoring.exception.GoalNotFoundException;
import com.wego.seolstudybe.mentoring.repository.GoalRepository;
import com.wego.seolstudybe.mentoring.repository.WorksheetFileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@Service
public class GoalServiceImpl implements GoalService {
    private final GoalRepository goalRepository;
    private final MemberRepository memberRepository;
    private final WorksheetFileRepository worksheetFileRepository;

    private final GoalMapper goalMapper;

    @Transactional
    @Override
    public Goal createGoal(final int memberId, final CreateGoalRequest request, final MultipartFile file) {
        final Member creator = findByMemberId(memberId);

        final Member targetMentee = findByMemberId(request.getMenteeId());

        final WorksheetFile worksheetFile = saveWorksheetFile(file, request.getSubject(), creator);

        final Goal goal = new Goal(worksheetFile, request.getName(), request.getSubject(), creator, targetMentee);

        return goalRepository.save(goal);
    }

    @Transactional
    @Override
    public void deleteGoal(final int memberId, final int goalId) {
        final Member member = findByMemberId(memberId);

        final Goal goal = findByGoalId(goalId);

        validateGoalCreator(member, goal);

        goal.softDelete(LocalDateTime.now());
    }

    @Transactional
    @Override
    public Goal updateGoal(final int memberId, final int goalId, final UpdateGoalRequest request,
                           final MultipartFile file) {
        final Member member = findByMemberId(memberId);

        final Goal goal = findByGoalId(goalId);

        validateGoalCreator(member, goal);

        final WorksheetFile worksheetFile = updateWorksheetFile(goal, file, request.isWorksheetChanged(), member,
                request.getSubject());

        goal.updateGoal(request.getName(), request.getSubject(), worksheetFile);

        return goal;
    }

    @Transactional(readOnly = true)
    @Override
    public List<GoalResponse> getGoals(final int memberId, final GoalCreator createdBy) {
        final Member member = findByMemberId(memberId);

        final List<GoalResponse> goals = goalMapper.findGoalsByCreatedBy(memberId, createdBy);

        return goals;
    }

    private WorksheetFile updateWorksheetFile(final Goal goal, final MultipartFile file,
                                              final boolean isWorksheetChanged, final Member member,
                                              final Subject subject) {
        final WorksheetFile worksheetFile = goal.getWorksheetFile();

        if (!isWorksheetChanged) {
            if (worksheetFile != null) {
                worksheetFile.updateSubject(subject);
            }

            return worksheetFile;
        }

        if (worksheetFile != null) {
            worksheetFileRepository.delete(worksheetFile);
        }

        return saveWorksheetFile(file, subject, member);
    }

    private void validateGoalCreator(final Member creator, final Goal goal) {
        if (creator.getId() != goal.getCreator().getId()) {
            throw new GoalAccessDeniedException();
        }
    }

    private WorksheetFile saveWorksheetFile(final MultipartFile file, final Subject subject, final Member member) {
        if (file == null || file.isEmpty()) {
            return null;
        }

        // TODO 파일 S3 업로드
        final String s3Url = "https://amazon.s3.bucket/file.pdf";

        final WorksheetFile worksheetFile = new WorksheetFile(member, file.getOriginalFilename(), s3Url,
                (float) file.getSize(), file.getContentType(), subject);

        return worksheetFileRepository.save(worksheetFile);
    }

    private Goal findByGoalId(final int goalId) {
        return goalRepository.findByIdAndDeletedAtIsNull(goalId).orElseThrow(GoalNotFoundException::new);
    }

    private Member findByMemberId(final int memberId) {
        return memberRepository.findById(memberId).orElseThrow(MemberNotFoundException::new);
    }
}