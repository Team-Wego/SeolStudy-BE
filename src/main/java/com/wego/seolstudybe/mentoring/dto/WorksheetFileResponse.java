package com.wego.seolstudybe.mentoring.dto;

import com.wego.seolstudybe.mentoring.entity.WorksheetFile;
import com.wego.seolstudybe.mentoring.entity.enums.Subject;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class WorksheetFileResponse {
    private int worksheetId;

    private String name;

    private String url;

    private float size;

    private String type;

    private Subject subject;

    public static WorksheetFileResponse of(final WorksheetFile worksheetFile) {
        return new WorksheetFileResponse(worksheetFile.getId(),
                worksheetFile.getName(), worksheetFile.getUrl(), worksheetFile.getSize(), worksheetFile.getType(),
                worksheetFile.getSubject());
    }
}