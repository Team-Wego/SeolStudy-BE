package com.wego.seolstudybe.common.controller;

import com.wego.seolstudybe.common.service.S3Service;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;

@Tag(name = "File", description = "파일 업로드 API")
@RestController
@RequestMapping("/files")
@RequiredArgsConstructor
public class FileUploadController {

    private final S3Service s3Service;

    @Operation(summary = "채팅 파일 업로드", description = "채팅에서 사용할 파일을 S3에 업로드합니다.")
    @PostMapping("/chat")
    public ResponseEntity<Map<String, String>> uploadChatFile(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

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
    @DeleteMapping
    public ResponseEntity<Void> deleteFile(@RequestParam("fileUrl") String fileUrl) {
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
