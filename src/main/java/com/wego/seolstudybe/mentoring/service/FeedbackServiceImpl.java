package com.wego.seolstudybe.mentoring.service;

import com.wego.seolstudybe.common.service.S3Service;
import com.wego.seolstudybe.member.entity.Member;
import com.wego.seolstudybe.member.entity.enums.Role;
import com.wego.seolstudybe.member.exception.MemberNotFoundException;
import com.wego.seolstudybe.member.repository.MemberRepository;
import com.wego.seolstudybe.mentoring.dto.*;
import com.wego.seolstudybe.mentoring.entity.Feedback;
import com.wego.seolstudybe.mentoring.entity.FeedbackImage;
import com.wego.seolstudybe.mentoring.entity.enums.FeedbackType;
import com.wego.seolstudybe.mentoring.exception.*;
import com.wego.seolstudybe.mentoring.repository.FeedbackImageRepository;
import com.wego.seolstudybe.mentoring.repository.FeedbackRepository;
import com.wego.seolstudybe.notification.entity.enums.NotificationType;
import com.wego.seolstudybe.notification.service.NotificationService;
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
    private final NotificationService notificationService;

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

        if (FeedbackType.TASK.equals(request.getType())) {
            if (feedbackRepository.existsByMenteeIdAndTargetDateAndTypeAndTaskId(
                    request.getMenteeId(), request.getTargetDate(), request.getType(), request.getTaskId())) {
                throw new FeedbackAlreadyExistException();
            }
        } else {
            if (feedbackRepository.existsByMenteeIdAndTargetDateAndType(
                    request.getMenteeId(), request.getTargetDate(), request.getType())) {
                throw new FeedbackAlreadyExistException();
            }
        }

        final Task task = findTaskById(request.getTaskId());

        if (request.getTaskId() != null) {
            task.updateFeedbackStatus(true);
        }

        Feedback feedback = Feedback.builder()
                .mentor(mentor)
                .mentee(mentee)
                .task(task)
                .type(request.getType())
                .targetDate(request.getTargetDate())
                .content(request.getContent())
                .highlight(request.getHighlight())
                .createdAt(LocalDateTime.now())
                .build();

        feedback = feedbackRepository.save(feedback);

        uploadFiles(files, feedback);

        // 멘티에게 피드백 알림 전송
        String notificationTitle = FeedbackType.TASK.equals(request.getType())
                ? "과제 피드백이 등록되었습니다"
                : "플래너 피드백이 등록되었습니다";
        notificationService.notify(
                (long) mentee.getId(),
                NotificationType.FEEDBACK,
                notificationTitle,
                feedback.getContent(),
                java.util.Map.of(
                        "type", "FEEDBACK",
                        "feedbackId", String.valueOf(feedback.getId()),
                        "feedbackType", request.getType().name()
                )
        );

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
                .map(f -> {
                    final List<FeedbackImageResponse> images = feedbackImageRepository.findByFeedbackId(f.getId())
                            .stream().map(FeedbackImageResponse::of).collect(Collectors.toList());
                    return FeedbackListResponse.of(f, images);
                })
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

    @Transactional(readOnly = true)
    @Override
    public FeedbackResponse getPlannerFeedback(final int memberId, final int menteeId, final LocalDate date) {
        final Member member = findMemberById(memberId);

        validateMenteeAccess(member, menteeId);

        final Feedback feedback = feedbackRepository.findByTargetDateAndMenteeIdAndType(date, menteeId, FeedbackType.PLANNER);

        if (feedback == null) {
            return null;
        }

        final List<FeedbackImageResponse> feedbackImages = feedbackImageRepository.findByFeedbackId(feedback.getId())
                .stream()
                .map(FeedbackImageResponse::of)
                .collect(Collectors.toList());

        return FeedbackResponse.of(feedback, feedbackImages);
    }

    @Transactional
    @Override
    public Feedback updateFeedback(final int memberId, final int feedbackId, final UpdateFeedbackRequest request,
                                   final List<MultipartFile> files) {
        final Member member = findMemberById(memberId);

        final Feedback feedback = findFeedbackById(feedbackId);

        if (member.getRole().equals(Role.MENTEE) || feedback.getMentor().getId() != member.getId()) {
            throw new FeedbackAccessDeniedException();
        }

        feedback.updateFeedback(request.getContent(), request.getHighlight());

        if (request.isImageChanged()) {
            updateFeedbackImages(files, feedback, request.getDeletedImageIds());
        }

        return feedback;
    }

    @Transactional
    @Override
    public void deleteFeedback(final int memberId, final int feedbackId) {
        final Member member = findMemberById(memberId);

        final Feedback feedback = findFeedbackById(feedbackId);

        if (member.getRole().equals(Role.MENTEE) || feedback.getMentor().getId() != member.getId()) {
            throw new FeedbackAccessDeniedException();
        }

        deleteFeedbackImages(feedbackImageRepository.findByFeedbackId(feedbackId));

        feedbackRepository.delete(feedback);
    }

    private void updateFeedbackImages(final List<MultipartFile> files, final Feedback feedback,
                                      final List<Integer> deletedImageIds) {
        if (files != null && !files.isEmpty()) {
            uploadFiles(files, feedback);
        }

        if (deletedImageIds != null) {
            final List<FeedbackImage> images = feedbackImageRepository.findByIdIn(deletedImageIds);

            deleteFeedbackImages(images);
        }
    }

    private void deleteFeedbackImages(final List<FeedbackImage> feedbackImages) {
        feedbackImages.forEach(feedbackImage -> s3Service.deleteFile(feedbackImage.getUrl()));

        feedbackImageRepository.deleteAll(feedbackImages);
    }

    private Feedback findFeedbackById(final int feedbackId) {
        return feedbackRepository.findById(feedbackId).orElseThrow(FeedbackNotFoundException::new);
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