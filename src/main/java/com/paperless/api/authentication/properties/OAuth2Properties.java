package com.paperless.api.authentication.properties;

import com.paperless.api.authentication.enums.OAuth2Provider;
import com.paperless.api.core.error.ExceptionCreator;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import static com.paperless.api.authentication.exception.OAuth2Exception.PROVIDER_NOT_SUPPORTED;

@Data
@Configuration
@ConfigurationProperties(prefix = "app.oauth2")
public class OAuth2Properties {
    private Provider google;
    private Provider kakao;

    @Data
    public static class Provider {
        private String clientId;
        private String clientSecret;

        private String authorizationUrl;
        private String tokenUrl;
        private String userInfoUrl;
        private String redirectUri;

        private String scope;
    }

    public Provider getProvider(OAuth2Provider provider) {
        return switch (provider) {
            case GOOGLE -> google;
            case KAKAO -> kakao;
            default -> throw ExceptionCreator.create(PROVIDER_NOT_SUPPORTED, "Provider: " + provider.getValue());
        };
    }
}
