package com.wego.seolstudybe.common.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(title = "SeolStudy API", description = "설스터디 API 문서")
)
public class SwaggerConfig {
}
