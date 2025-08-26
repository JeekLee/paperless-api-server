package com.paperless.api.core.data.aws.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "aws.credentials")
@Getter
@Setter
public class AwsCredentialProperties {
    private String accessKey;
    private String secretKey;
}
