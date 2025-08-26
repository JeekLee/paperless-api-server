package com.paperless.api.authentication.presentation.request;

import com.paperless.api.authentication.enums.OAuth2Provider;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class OAuth2UrlRequest {
    @NotNull(message = "Provider required")
    @Schema(description = "OAuth2 Provider", example = "GOOGLE")
    private OAuth2Provider provider;

    @NotBlank(message = "Code verifier required")
    @Size(min = 43, max = 128, message = "Code verifier must be between 43 and 128 characters")
    @Pattern(regexp = "^[A-Za-z0-9\\-._~]+$", message = "Invalid code verifier format")
    private String codeVerifier;

    @NotBlank(message = "RedirectPath required")
    @Pattern(regexp = "^/[a-zA-Z0-9._~!$&'()*+,;=:@/-]*$", message = "Invalid relative path format")
    @Schema(description = "Relative path of client application", example = "/home")
    private String redirectPath;
}
