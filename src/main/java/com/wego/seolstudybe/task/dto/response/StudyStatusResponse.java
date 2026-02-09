package com.wego.seolstudybe.task.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class StudyStatusResponse {

    private int achievementRate;
    private int totalTaskCount;
    private int completedTaskCount;
    private List<SubjectStudyStatusResponse> subjects;

    public static StudyStatusResponse from(List<SubjectStudyStatusResponse> subjects) {
        int total = subjects.stream().mapToInt(SubjectStudyStatusResponse::getTotalTaskCount).sum();
        int completed = subjects.stream().mapToInt(SubjectStudyStatusResponse::getCompletedTaskCount).sum();
        int rate = total == 0 ? 0 : (int) Math.round((double) completed / total * 100);
        return new StudyStatusResponse(rate, total, completed, subjects);
    }
}
