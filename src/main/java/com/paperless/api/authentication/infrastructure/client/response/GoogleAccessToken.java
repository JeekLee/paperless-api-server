package com.paperless.api.authentication.infrastructure.client.response;

import com.paperless.api.authentication.domain.model.OAuth2AccessToken;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class GoogleAccessToken extends OAuth2AccessToken {
    @JsonProperty("access_token")
    private String accessToken;

    @JsonProperty("expires_in")
    private Long expiresIn;
}
