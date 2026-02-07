package com.wego.seolstudybe.task.dto.response;

import com.wego.seolstudybe.mentoring.entity.enums.Subject;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class WorksheetFileDto {

    private int id;
    private String name;
    private String url;
    private Float size;
    private String type;
    private Subject subject;
}
