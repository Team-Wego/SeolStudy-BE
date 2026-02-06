package com.wego.seolstudybe.mentoring.service;

import com.wego.seolstudybe.common.service.S3Service;
import com.wego.seolstudybe.member.entity.Member;
import com.wego.seolstudybe.member.entity.enums.Role;
import com.wego.seolstudybe.member.exception.MemberNotFoundException;
import com.wego.seolstudybe.member.repository.MemberRepository;
import com.wego.seolstudybe.mentoring.dto.CreateFeedbackRequest;
import com.wego.seolstudybe.mentoring.dto.DailyFeedbackCountResponse;
import com.wego.seolstudybe.mentoring.dto.FeedbackImageResponse;
import com.wego.seolstudybe.mentoring.dto.FeedbackListResponse;
import com.wego.seolstudybe.mentoring.dto.FeedbackResponse;
import com.wego.seolstudybe.mentoring.entity.Feedback;
import com.wego.seolstudybe.mentoring.entity.FeedbackImage;
import com.wego.seolstudybe.mentoring.entity.enums.FeedbackType;
import com.wego.seolstudybe.mentoring.exception.FeedbackAccessDeniedException;
import com.wego.seolstudybe.mentoring.exception.FeedbackNotFoundException;
import com.wego.seolstudybe.mentoring.exception.TaskIdRequiredException;
import com.wego.seolstudybe.mentoring.repository.FeedbackImageRepository;
import com.wego.seolstudybe.mentoring.repository.FeedbackRepository;
import com.wego.seolstudybe.task.entity.Task;
import com.wego.seolstudybe.task.repository.TaskRepository;
import com.wego.seolstudybe.task.exception.TaskNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class FeedbackServiceImpl implements FeedbackService {
    private final FeedbackRepository feedbackRepository;
    private final FeedbackImageRepository feedbackImageRepository;
    private final MemberRepository memberRepository;
    private final TaskRepository taskRepository;
    private final S3Service s3Service;

    private static final String FEEDBACK_FOLDER = "feedback";

    @Transactional
    @Override
    public Feedback createFeedback(final int mentorId, final CreateFeedbackRequest request,
                                   final List<MultipartFile> files) {
        if (FeedbackType.TASK.equals(request.getType()) && request.getTaskId() == null) {
            throw new TaskIdRequiredException();
        }

        final Member mentor = findMemberById(mentorId);

        final Member mentee = findMemberById(request.getMenteeId());

        final Task task = findTaskById(request.getTaskId());

        Feedback feedback = Feedback.builder()
                .mentor(mentor)
                .mentee(mentee)
                .task(task)
                .type(request.getType())
                .targetDate(request.getTargetDate())
                .content(request.getContent())
                .createdAt(LocalDateTime.now())
                .build();

        feedback = feedbackRepository.save(feedback);

        uploadFiles(files, feedback);

        return feedback;
    }

    @Transactional(readOnly = true)
    @Override
    public FeedbackResponse getFeedback(final int memberId, final int feedbackId) {
        final Member member = findMemberById(memberId);

        final Feedback feedback = feedbackRepository.findById(feedbackId).orElseThrow(FeedbackNotFoundException::new);

        if (member.getRole().equals(Role.MENTEE) && feedback.getMentee().getId() != member.getId()) {
            throw new FeedbackNotFoundException();
        }

        final List<FeedbackImageResponse> feedbackImages = feedbackImageRepository.findByFeedbackId(feedback.getId())
                .stream()
                .map(FeedbackImageResponse::of)
                .collect(Collectors.toList());

        return FeedbackResponse.of(feedback, feedbackImages);
    }

    @Transactional(readOnly = true)
    @Override
    public List<FeedbackListResponse> getFeedbackList(final int memberId, final int menteeId, final FeedbackType type) {
        final Member member = findMemberById(memberId);

        validateMenteeAccess(member, menteeId);

        final List<Feedback> feedbacks = (type != null)
                ? feedbackRepository.findByMenteeIdAndTypeOrderByCreatedAtDesc(menteeId, type)
                : feedbackRepository.findByMenteeIdOrderByCreatedAtDesc(menteeId);

        return feedbacks.stream()
                .map(FeedbackListResponse::of)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    @Override
    public List<DailyFeedbackCountResponse> getDailyFeedbackCount(final int memberId, final int menteeId,
                                                                   final LocalDate startDate, final LocalDate endDate) {
        final Member member = findMemberById(memberId);

        validateMenteeAccess(member, menteeId);

        return feedbackRepository.countDailyByMenteeIdAndTargetDateBetween(menteeId, startDate, endDate);
    }

    private void validateMenteeAccess(final Member member, final int menteeId) {
        if (member.getRole().equals(Role.MENTEE) && member.getId() != menteeId) {
            throw new FeedbackAccessDeniedException();
        }
    }

    private Task findTaskById(final Integer taskId) {
        if (taskId == null) {
            return null;
        }

        return taskRepository.findById(taskId).orElseThrow(TaskNotFoundException::new);
    }

    private void uploadFiles(final List<MultipartFile> files, final Feedback feedback) {
        if (files == null || files.isEmpty()) {
            return;
        }

        List<FeedbackImage> feedbackImages = new ArrayList<>();

        for (MultipartFile file : files) {
            if (file == null || file.isEmpty()) {
                continue;
            }

            final String fileUrl = s3Service.uploadFile(file, FEEDBACK_FOLDER);

            final FeedbackImage feedbackImage = FeedbackImage.builder()
                    .feedback(feedback)
                    .url(fileUrl)
                    .build();

            feedbackImages.add(feedbackImage);
        }

        feedbackImageRepository.saveAll(feedbackImages);
    }

    private Member findMemberById(final int memberId) {
        return memberRepository.findById(memberId).orElseThrow(MemberNotFoundException::new);
    }
}