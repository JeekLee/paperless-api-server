package com.paperless.api.core.web.properties;

import lombok.Getter;
import org.springframework.stereotype.Component;

import static org.springframework.http.HttpHeaders.CONTENT_DISPOSITION;

@Component
@Getter
public class CorsProperties {
    private final String[] allowedOrigins = {
            "http://localhost:3000",
            "https://localhost:3000",
            "http://localhost:8080",
            "http://localhost:63342",

            "https://dev-api.connectforme.com",
            "https://dev-web.connectforme.com",
    };
    private final String[] allowedMethods = {"GET", "POST", "PATCH", "DELETE", "PUT", "OPTIONS"};
    private final String[] allowedHeaders = {"*"};
    private final String[] exposedHeaders = {CONTENT_DISPOSITION};
}
