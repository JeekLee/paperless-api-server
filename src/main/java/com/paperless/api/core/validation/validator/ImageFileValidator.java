package com.paperless.api.core.validation.validator;

import com.paperless.api.core.validation.annotation.ValidImageFile;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.List;

public class ImageFileValidator implements ConstraintValidator<ValidImageFile, MultipartFile> {

    private List<String> allowedTypes;
    private long maxSizeBytes;

    @Override
    public void initialize(ValidImageFile constraintAnnotation) {
        this.allowedTypes = Arrays.asList(constraintAnnotation.allowedTypes());
        this.maxSizeBytes = constraintAnnotation.maxSizeBytes();
    }

    @Override
    public boolean isValid(MultipartFile file, ConstraintValidatorContext context) {
        if (file == null || file.isEmpty()) {
            addConstraintViolation(context, "파일 정보가 존재하지 않습니다.");
            return false;
        }

        if (file.getSize() > maxSizeBytes) {
            addConstraintViolation(context, String.format("파일 크기가 너무 큽니다. 최대 크기: %d bytes", maxSizeBytes));
            return false;
        }

        String contentType = file.getContentType();
        if (contentType == null || !allowedTypes.contains(contentType.toLowerCase())) {
            addConstraintViolation(context, String.format("지원하지 않는 파일 형식입니다. 지원 형식: %s", allowedTypes));
            return false;
        }

        String originalFilename = file.getOriginalFilename();
        if (originalFilename != null && !isValidImageExtension(originalFilename)) {
            addConstraintViolation(context, "유효하지 않은 이미지 파일 확장자입니다.");
            return false;
        }

        if (!isValidImageSignature(file)) {
            addConstraintViolation(context, "파일 내용이 이미지 형식과 일치하지 않습니다.");
            return false;
        }

        return true;
    }

    private void addConstraintViolation(ConstraintValidatorContext context, String message) {
        context.disableDefaultConstraintViolation();
        context.buildConstraintViolationWithTemplate(message).addConstraintViolation();
    }

    private boolean isValidImageExtension(String filename) {
        String extension = getFileExtension(filename).toLowerCase();
        return Arrays.asList("jpg", "jpeg", "png", "gif").contains(extension);
    }

    private String getFileExtension(String filename) {
        int lastDotIndex = filename.lastIndexOf('.');
        return lastDotIndex > 0 ? filename.substring(lastDotIndex + 1) : "";
    }

    private boolean isValidImageSignature(MultipartFile file) {
        try {
            byte[] header = new byte[8];
            int bytesRead = file.getInputStream().read(header);

            if (bytesRead < 4) {
                return false;
            }

            // JPEG 시그니처: FF D8 FF
            if (header[0] == (byte) 0xFF && header[1] == (byte) 0xD8 && header[2] == (byte) 0xFF) {
                return true;
            }

            // PNG 시그니처: 89 50 4E 47
            if (header[0] == (byte) 0x89 && header[1] == 0x50 && header[2] == 0x4E && header[3] == 0x47) {
                return true;
            }

            // GIF 시그니처: 47 49 46 38
            if (header[0] == 0x47 && header[1] == 0x49 && header[2] == 0x46 && header[3] == 0x38) {
                return true;
            }

            return false;
        } catch (Exception e) {
            return false;
        }
    }
}
