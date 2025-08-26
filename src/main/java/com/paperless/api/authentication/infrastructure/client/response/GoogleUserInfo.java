package com.paperless.api.authentication.infrastructure.client.response;

import com.paperless.api.authentication.domain.model.OAuth2UserInfo;
import com.paperless.api.authentication.enums.OAuth2Provider;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class GoogleUserInfo extends OAuth2UserInfo {
    @JsonIgnore
    private final OAuth2Provider provider = OAuth2Provider.GOOGLE;

    @JsonProperty("sub")
    private String providerId;

    @JsonProperty("email")
    private String email;

    @JsonProperty("picture")
    private String imageUrl;
}
