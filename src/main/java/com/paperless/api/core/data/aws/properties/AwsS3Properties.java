package com.paperless.api.core.data.aws.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "aws.s3")
@Getter
@Setter
public class AwsS3Properties {
    private String bucket;
}
