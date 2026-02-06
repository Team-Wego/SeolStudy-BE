package com.wego.seolstudybe.common.service;

import com.wego.seolstudybe.common.error.ErrorCode;
import com.wego.seolstudybe.common.error.exception.FileUploadException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.IOException;
import java.util.Set;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class S3Service {

    private final S3Client s3Client;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    @Value("${cloud.aws.region.static}")
    private String region;

    // 최대 파일 크기: 50MB
    private static final long MAX_FILE_SIZE = 50 * 1024 * 1024;

    // 허용되는 파일 확장자
    private static final Set<String> ALLOWED_EXTENSIONS = Set.of(
            // 이미지
            ".jpg", ".jpeg", ".png", ".gif", ".webp", ".bmp",
            // 동영상
            ".mp4", ".mov", ".avi", ".webm", ".mkv",
            // 문서
            ".pdf", ".doc", ".docx", ".xls", ".xlsx", ".ppt", ".pptx", ".txt", ".hwp",
            // 압축
            ".zip", ".rar", ".7z",
            // 기타
            ".json", ".xml", ".csv"
    );

    /**
     * 파일 업로드
     * @param file 업로드할 파일
     * @param folder 저장할 폴더 (예: "chat")
     * @return 업로드된 파일의 URL
     */
    public String uploadFile(MultipartFile file, String folder) {
        // 파일 유효성 검사
        validateFile(file);

        String originalFilename = file.getOriginalFilename();
        String extension = getExtension(originalFilename);
        String fileName = folder + "/" + UUID.randomUUID() + extension;

        try {
            PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                    .bucket(bucket)
                    .key(fileName)
                    .contentType(file.getContentType())
                    .build();

            s3Client.putObject(putObjectRequest, RequestBody.fromBytes(file.getBytes()));

            String fileUrl = String.format("https://%s.s3.%s.amazonaws.com/%s", bucket, region, fileName);
            log.info("파일 업로드 완료: {}", fileUrl);

            return fileUrl;

        } catch (IOException e) {
            log.error("파일 읽기 실패: {}", e.getMessage());
            throw new FileUploadException(ErrorCode.FILE_UPLOAD_FAILED, "파일을 읽는 중 오류가 발생했습니다.");
        } catch (Exception e) {
            log.error("S3 업로드 실패: {}", e.getMessage());
            throw new FileUploadException(ErrorCode.FILE_UPLOAD_FAILED, "S3 업로드 중 오류가 발생했습니다.");
        }
    }

    /**
     * 파일 삭제
     * @param fileUrl 삭제할 파일의 URL
     */
    public void deleteFile(String fileUrl) {
        if (fileUrl == null || fileUrl.isBlank()) {
            throw new FileUploadException(ErrorCode.BAD_REQUEST, "파일 URL이 비어있습니다.");
        }

        try {
            String fileName = extractFileName(fileUrl);

            DeleteObjectRequest deleteObjectRequest = DeleteObjectRequest.builder()
                    .bucket(bucket)
                    .key(fileName)
                    .build();

            s3Client.deleteObject(deleteObjectRequest);
            log.info("파일 삭제 완료: {}", fileName);
        } catch (Exception e) {
            log.error("S3 파일 삭제 실패: {}", e.getMessage());
            throw new FileUploadException(ErrorCode.FILE_DELETE_FAILED);
        }
    }

    /**
     * 파일 유효성 검사
     */
    private void validateFile(MultipartFile file) {
        // 빈 파일 체크
        if (file == null || file.isEmpty()) {
            throw new FileUploadException(ErrorCode.FILE_EMPTY);
        }

        // 파일 크기 체크
        if (file.getSize() > MAX_FILE_SIZE) {
            throw new FileUploadException(ErrorCode.FILE_SIZE_EXCEEDED,
                    String.format("파일 크기가 제한(%dMB)을 초과했습니다.", MAX_FILE_SIZE / 1024 / 1024));
        }

        // 파일 확장자 체크
        String extension = getExtension(file.getOriginalFilename()).toLowerCase();
        if (!extension.isEmpty() && !ALLOWED_EXTENSIONS.contains(extension)) {
            throw new FileUploadException(ErrorCode.INVALID_FILE_TYPE,
                    "허용되지 않는 파일 형식입니다: " + extension);
        }
    }

    /**
     * 파일 확장자 추출
     */
    private String getExtension(String filename) {
        if (filename == null || !filename.contains(".")) {
            return "";
        }
        return filename.substring(filename.lastIndexOf("."));
    }

    /**
     * URL에서 파일명 추출
     */
    private String extractFileName(String fileUrl) {
        return fileUrl.substring(fileUrl.indexOf(".com/") + 5);
    }

    /**
     * 파일 타입 확인 (이미지인지)
     */
    public boolean isImageFile(String contentType) {
        return contentType != null && contentType.startsWith("image/");
    }

    /**
     * 파일 타입 확인 (비디오인지)
     */
    public boolean isVideoFile(String contentType) {
        return contentType != null && contentType.startsWith("video/");
    }
}
