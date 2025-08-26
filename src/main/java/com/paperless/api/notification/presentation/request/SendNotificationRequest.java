package com.paperless.api.notification.presentation.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SendNotificationRequest {
    private Long memberId;
    private String message;

    @Pattern(regexp = "^/[a-zA-Z0-9._~!$&'()*+,;=:@/-]*$", message = "Invalid relative path format")
    @Schema(description = "Relative path of client application", example = "/home")
    private String redirectPath;
}
