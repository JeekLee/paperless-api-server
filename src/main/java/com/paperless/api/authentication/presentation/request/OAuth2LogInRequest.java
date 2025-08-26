package com.paperless.api.authentication.presentation.request;

import com.paperless.api.authentication.enums.OAuth2Provider;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class OAuth2LogInRequest {
    @NotNull(message = "Provider required")
    @Schema(description = "OAuth2 Provider", example = "GOOGLE")
    private OAuth2Provider provider;

    @NotBlank(message = "Authorization code required")
    private String code;

    @NotBlank(message = "State required")
    @Pattern(regexp = "^[A-Za-z0-9_-]{43}$", message = "State must be 43 characters")
    private String state;
}
