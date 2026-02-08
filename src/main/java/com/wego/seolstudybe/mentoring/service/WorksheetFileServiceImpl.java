package com.wego.seolstudybe.mentoring.service;

import com.wego.seolstudybe.mentoring.dao.WorksheetFileMapper;
import com.wego.seolstudybe.mentoring.dto.WorksheetFileResponse;
import com.wego.seolstudybe.mentoring.entity.enums.Subject;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class WorksheetFileServiceImpl implements WorksheetFileService {
    private final WorksheetFileMapper worksheetFileMapper;

    @Transactional(readOnly = true)
    @Override
    public List<WorksheetFileResponse> getWorksheetFiles(final int memberId, final String keyword, final Subject subject) {
        return worksheetFileMapper.findByMenteeIdAndKeywordAndSubject(memberId, keyword, subject);
    }
}