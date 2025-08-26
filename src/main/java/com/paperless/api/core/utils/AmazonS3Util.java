package com.paperless.api.core.utils;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.paperless.api.core.data.aws.properties.AwsS3Properties;
import com.paperless.api.core.error.ExceptionCreator;
import com.paperless.api.core.utils.execption.S3Exception;
import com.github.f4b6a3.ulid.UlidCreator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

@Component
@RequiredArgsConstructor
public class AmazonS3Util {
    private final AmazonS3 amazonS3;
    private final AwsS3Properties awsS3Properties;

    public String upload(String directory, MultipartFile file) {
        if (file == null || file.isEmpty() || file.getOriginalFilename() == null) {
            throw ExceptionCreator.create(S3Exception.INVALID_FILE);
        }

        String originalFilename = file.getOriginalFilename();
        String fileExtension = originalFilename.substring(originalFilename.lastIndexOf('.') + 1).toLowerCase();
        String imageName = UlidCreator.getUlid().toString() + '.' + fileExtension;

        String key = directory + "/" + imageName;

        try (InputStream inputStream = file.getInputStream()) {
            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentType(file.getContentType());
            metadata.setContentLength(file.getSize());

            amazonS3.putObject(new PutObjectRequest(awsS3Properties.getBucket(), key, inputStream, metadata));
        }
        catch (IOException e) {
            throw ExceptionCreator.create(S3Exception.UPLOAD_FAILED, "Key: " + key);
        }

        return key;
    }

    public void delete(String key) {
        amazonS3.deleteObject(awsS3Properties.getBucket(), key);
    }

}
