package com.wego.seolstudybe.mentoring.service;

import com.wego.seolstudybe.mentoring.dto.WorksheetFileResponse;
import com.wego.seolstudybe.mentoring.entity.WorksheetFile;
import com.wego.seolstudybe.mentoring.entity.enums.Subject;
import com.wego.seolstudybe.mentoring.repository.GoalRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class WorksheetFileServiceImpl implements WorksheetFileService {
    private final GoalRepository goalRepository;

    @Transactional(readOnly = true)
    @Override
    public List<WorksheetFileResponse> getWorksheetFiles(final int memberId, final Subject subject) {
        List<WorksheetFile> files;

        if (subject != null) {
            files = goalRepository.findWorksheetFileByMenteeIdAndSubject(memberId, subject);
        } else {
            files = goalRepository.findWorksheetFileByMenteeId(memberId);
        }

        return files.stream()
                .map(WorksheetFileResponse::of)
                .collect(Collectors.toList());
    }
}