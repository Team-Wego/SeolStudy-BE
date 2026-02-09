package com.wego.seolstudybe.task.dto.response;

import com.wego.seolstudybe.task.entity.TaskImage;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class TaskImageDto {

    private int id;
    private String url;

    public static TaskImageDto from(TaskImage taskImage) {
        return new TaskImageDto(taskImage.getId(), taskImage.getUrl());
    }
}
