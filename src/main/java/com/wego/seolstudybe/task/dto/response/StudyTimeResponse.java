package com.wego.seolstudybe.task.dto.response;

import com.wego.seolstudybe.task.entity.StudyTime;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class StudyTimeResponse {

    private final int id;
    private final int taskId;
    private final LocalDateTime startedAt;
    private final LocalDateTime endedAt;

    public static StudyTimeResponse from(StudyTime studyTime) {
        return StudyTimeResponse.builder()
                .id(studyTime.getId())
                .taskId(studyTime.getTask().getId())
                .startedAt(studyTime.getStartedAt())
                .endedAt(studyTime.getEndedAt())
                .build();
    }
}
