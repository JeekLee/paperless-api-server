package com.paperless.api.member.presentation.request;

import com.paperless.api.core.validation.annotation.ValidImageFile;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateImageRequest {
    @ValidImageFile(
            allowedTypes = {"image/jpeg", "image/jpg", "image/png"},
            maxSizeBytes = 5 * 1024 * 1024
    )
    private MultipartFile imageFile;
}
