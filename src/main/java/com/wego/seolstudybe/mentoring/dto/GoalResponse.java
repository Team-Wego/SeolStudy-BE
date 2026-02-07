package com.wego.seolstudybe.mentoring.dto;

import com.wego.seolstudybe.mentoring.entity.enums.Subject;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class GoalResponse {
    private int goalId;

    private int creatorId;

    private String name;

    private Subject subject;

    private List<WorksheetFileResponse> worksheetFiles;
}