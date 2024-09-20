package com.riwi.sitekeeper.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.security.SecuritySchemes;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(info = @Info(
        title = "SiteKeeper API",
        version = "1.0",
        description = "API to manage SiteKeeper"
    ),
        security = @SecurityRequirement(name = "bearerAuth")
    )
    @SecuritySchemes({
            @SecurityScheme(
                    name = "bearerAuth",
                    type = SecuritySchemeType.HTTP,
                    scheme = "bearer",
                    bearerFormat = "JWT"
            )
    })

public class OpenApiConfiguration {
}
