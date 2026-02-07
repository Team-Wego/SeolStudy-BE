package com.wego.seolstudybe.task.service;

import com.wego.seolstudybe.task.dto.request.StudyTimeCreateRequest;
import com.wego.seolstudybe.task.dto.request.StudyTimeUpdateRequest;
import com.wego.seolstudybe.task.dto.response.StudyTimeResponse;

public interface StudyTimeService {

    StudyTimeResponse createStudyTime(int menteeId, StudyTimeCreateRequest request);

    StudyTimeResponse updateStudyTime(int menteeId, int studyTimeId, StudyTimeUpdateRequest request);

    void deleteStudyTime(int menteeId, int studyTimeId);
}
