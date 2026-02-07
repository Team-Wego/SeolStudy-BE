package com.wego.seolstudybe.task.dto.response;

import com.wego.seolstudybe.mentoring.entity.enums.Subject;
import com.wego.seolstudybe.task.entity.enums.TaskType;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@NoArgsConstructor
public class TaskDetailResponse {

    private int id;
    private String title;
    private String description;
    private boolean isChecked;
    private Subject subject;
    private TaskType type;
    private LocalDate date;
    private String comment;
    private boolean hasFeedback;
    private Integer sequence;
    private LocalDateTime checkedAt;
    private LocalDateTime submittedAt;
    private LocalDateTime createdAt;
    private Integer goalId;
    private List<WorksheetFileDto> worksheetFiles;
    private List<TaskImageDto> images;

    public void setWorksheetFiles(List<WorksheetFileDto> worksheetFiles) {
        this.worksheetFiles = worksheetFiles;
    }

    public void setImages(List<TaskImageDto> images) {
        this.images = images;
    }
}
