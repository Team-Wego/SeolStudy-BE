package com.wego.seolstudybe.task.service;

import com.wego.seolstudybe.task.dto.response.TaskImageDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface TaskImageService {

    List<TaskImageDto> uploadTaskImages(int menteeId, int taskId, List<MultipartFile> files);
}
