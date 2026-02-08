package com.wego.seolstudybe.mentoring.service;

import com.wego.seolstudybe.mentoring.dto.WorksheetFileResponse;
import com.wego.seolstudybe.mentoring.entity.enums.Subject;

import java.util.List;

public interface WorksheetFileService {
    List<WorksheetFileResponse> getWorksheetFiles(final int memberId, final String keyword, final Subject subject);
}