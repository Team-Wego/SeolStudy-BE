package com.wego.seolstudybe.mentoring.controller;

import com.wego.seolstudybe.mentoring.dto.WorksheetFileResponse;
import com.wego.seolstudybe.mentoring.entity.enums.Subject;
import com.wego.seolstudybe.mentoring.service.WorksheetFileService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "자료실 - Worksheet", description = "자료실 관련 API")
@RequiredArgsConstructor
@RestController
public class WorksheetFileController {
    private final WorksheetFileService worksheetFileService;

    @GetMapping("/mentees/worksheets")
    public ResponseEntity<List<WorksheetFileResponse>> getWorksheetFiles(@CookieValue("memberId") final int memberId,
                                                                         @RequestParam(value = "subject", required = false) final Subject subject) {
        List<WorksheetFileResponse> responses = worksheetFileService.getWorksheetFiles(memberId, subject);

        return ResponseEntity.ok(responses);
    }
}