package com.wego.seolstudybe.task.dto.response;

import com.wego.seolstudybe.mentoring.entity.enums.Subject;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class SubjectStudyStatusResponse {

    private Subject subject;
    private int totalTaskCount;
    private int completedTaskCount;

    public int getAchievementRate() {
        if (totalTaskCount == 0) {
            return 0;
        }
        return (int) Math.round((double) completedTaskCount / totalTaskCount * 100);
    }
}
