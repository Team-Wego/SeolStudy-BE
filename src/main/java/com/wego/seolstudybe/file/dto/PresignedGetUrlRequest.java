package com.wego.seolstudybe.file.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class PresignedGetUrlRequest {
    @NotBlank
    private String s3Url;

    private String fileName;
}