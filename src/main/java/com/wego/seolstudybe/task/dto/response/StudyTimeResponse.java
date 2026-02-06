package com.wego.seolstudybe.task.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class StudyTimeResponse {

    private LocalDate date;
    private int totalMinutes;
    private List<StudyTimeDetailDto> studyTimes;
}
