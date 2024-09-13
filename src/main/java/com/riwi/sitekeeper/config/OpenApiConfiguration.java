package com.riwi.sitekeeper.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(info = @Info(
        title = "SiteKeeper API",
        version = "1.0",
        description = "API to manage SiteKeeper"
))
public class OpenApiConfiguration {
}
