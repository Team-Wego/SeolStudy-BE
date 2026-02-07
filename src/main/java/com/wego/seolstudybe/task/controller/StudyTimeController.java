package com.wego.seolstudybe.task.controller;

import com.wego.seolstudybe.task.dto.request.StudyTimeCreateRequest;
import com.wego.seolstudybe.task.dto.request.StudyTimeUpdateRequest;
import com.wego.seolstudybe.task.dto.response.StudyTimeResponse;
import com.wego.seolstudybe.task.service.StudyTimeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "공부 시간 관리 API", description = "멘티 공부 시간 관리 API")
@RestController
@RequestMapping("/mentees/{menteeId}/study-times")
@RequiredArgsConstructor
public class StudyTimeController {

    private final StudyTimeService studyTimeService;

    @PostMapping
    @Operation(summary = "공부 시간 등록", description = "할 일 또는 과목 항목에 대해 공부 시간을 체크하며, 시작 시간과 종료 시간을 등록합니다.")
    public ResponseEntity<StudyTimeResponse> createStudyTime(
            @PathVariable("menteeId") int menteeId,
            @Valid @RequestBody StudyTimeCreateRequest request
    ) {
        StudyTimeResponse response = studyTimeService.createStudyTime(menteeId, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{studyTimeId}")
    @Operation(summary = "공부 시간 수정", description = "할 일 또는 과목 항목에 대한 공부 시간을 수정합니다.")
    public ResponseEntity<StudyTimeResponse> updateStudyTime(
            @PathVariable("menteeId") int menteeId,
            @PathVariable("studyTimeId") int studyTimeId,
            @Valid @RequestBody StudyTimeUpdateRequest request
    ) {
        StudyTimeResponse response = studyTimeService.updateStudyTime(menteeId, studyTimeId, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{studyTimeId}")
    @Operation(summary = "공부 시간 삭제", description = "할 일 또는 과목 항목에 대한 공부 시간을 삭제합니다.")
    public ResponseEntity<Void> deleteStudyTime(
            @PathVariable("menteeId") int menteeId,
            @PathVariable("studyTimeId") int studyTimeId
    ) {
        studyTimeService.deleteStudyTime(menteeId, studyTimeId);
        return ResponseEntity.noContent().build();
    }
}
