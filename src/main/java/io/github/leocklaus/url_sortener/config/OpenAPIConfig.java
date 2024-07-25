package io.github.leocklaus.url_sortener.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityScheme;

@OpenAPIDefinition(
        info = @Info(
                title = "URL Shortener API Documentation",
                contact = @Contact(
                        name = "Leonardo Klaus",
                        url = "https://github.com/leocklaus"
                ),
                version = "1.0"
        )
)
@SecurityScheme(
        name = "bearerAuth",
        description = "A valid user JWT token should be provided",
        scheme = "bearer",
        type = SecuritySchemeType.HTTP,
        bearerFormat = "JWT",
        in = SecuritySchemeIn.HEADER
)
public class OpenAPIConfig {
}
