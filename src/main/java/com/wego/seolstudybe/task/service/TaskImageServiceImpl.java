package com.wego.seolstudybe.task.service;

import com.wego.seolstudybe.common.service.S3Service;
import com.wego.seolstudybe.task.dto.response.TaskImageDto;
import com.wego.seolstudybe.task.entity.Task;
import com.wego.seolstudybe.task.entity.TaskImage;
import com.wego.seolstudybe.task.exception.TaskImageUploadPeriodExpiredException;
import com.wego.seolstudybe.task.exception.TaskNotFoundException;
import com.wego.seolstudybe.task.repository.TaskImageRepository;
import com.wego.seolstudybe.task.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TaskImageServiceImpl implements TaskImageService {

    private final TaskRepository taskRepository;
    private final TaskImageRepository taskImageRepository;
    private final S3Service s3Service;

    @Override
    @Transactional
    public List<TaskImageDto> uploadTaskImages(int menteeId, int taskId, List<MultipartFile> files) {
        Task task = taskRepository.findByIdAndMenteeId(taskId, menteeId)
                .orElseThrow(TaskNotFoundException::new);

        validateUploadPeriod(task);

        List<TaskImageDto> result = new ArrayList<>();
        for (MultipartFile file : files) {
            String imageUrl = s3Service.uploadFile(file, "task-images");

            TaskImage taskImage = TaskImage.builder()
                    .task(task)
                    .url(imageUrl)
                    .build();

            TaskImage saved = taskImageRepository.save(taskImage);
            result.add(TaskImageDto.from(saved));
        }

        return result;
    }

    private void validateUploadPeriod(Task task) {
        // 과제 날짜 + 1일 오전 6시까지 업로드 가능
        LocalDateTime deadline = task.getDate().plusDays(1).atTime(6, 0);
        LocalDateTime now = LocalDateTime.now();

        if (now.isAfter(deadline)) {
            throw new TaskImageUploadPeriodExpiredException();
        }
    }
}
