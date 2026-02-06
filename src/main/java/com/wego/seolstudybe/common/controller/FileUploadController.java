package com.wego.seolstudybe.common.controller;

import com.wego.seolstudybe.common.service.S3Service;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Tag(name = "File", description = "파일 업로드 API")
@RestController
@RequestMapping("/files")
@RequiredArgsConstructor
public class FileUploadController {

    private final S3Service s3Service;

    @Operation(summary = "채팅 파일 업로드", description = "채팅에서 사용할 파일을 S3에 업로드합니다. (최대 50MB)")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "업로드 성공"),
            @ApiResponse(responseCode = "400", description = "파일이 비어있거나 허용되지 않는 파일 형식"),
            @ApiResponse(responseCode = "500", description = "S3 업로드 실패")
    })
    @PostMapping("/chat")
    public ResponseEntity<Map<String, String>> uploadChatFile(@RequestParam("file") MultipartFile file) {
        log.info("파일 업로드 요청: fileName={}, size={}bytes", file.getOriginalFilename(), file.getSize());

        // S3Service에서 유효성 검사 및 업로드 수행 (예외 발생 시 GlobalExceptionHandler에서 처리)
        String fileUrl = s3Service.uploadFile(file, "chat");
        String fileName = file.getOriginalFilename();
        String fileType = determineFileType(file.getContentType());

        Map<String, String> response = new HashMap<>();
        response.put("fileUrl", fileUrl);
        response.put("fileName", fileName);
        response.put("fileType", fileType);

        return ResponseEntity.ok(response);
    }

    @Operation(summary = "파일 삭제", description = "S3에서 파일을 삭제합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "삭제 성공"),
            @ApiResponse(responseCode = "400", description = "잘못된 파일 URL"),
            @ApiResponse(responseCode = "500", description = "S3 삭제 실패")
    })
    @DeleteMapping
    public ResponseEntity<Void> deleteFile(@RequestParam("fileUrl") String fileUrl) {
        log.info("파일 삭제 요청: fileUrl={}", fileUrl);
        s3Service.deleteFile(fileUrl);
        return ResponseEntity.ok().build();
    }

    /**
     * 파일 타입 결정 (IMAGE, VIDEO, FILE)
     */
    private String determineFileType(String contentType) {
        if (contentType == null) {
            return "FILE";
        }
        if (contentType.startsWith("image/")) {
            return "IMAGE";
        }
        if (contentType.startsWith("video/")) {
            return "VIDEO";
        }
        return "FILE";
    }
}
