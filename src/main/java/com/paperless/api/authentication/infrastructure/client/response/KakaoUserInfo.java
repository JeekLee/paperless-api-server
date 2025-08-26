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
public class KakaoUserInfo extends OAuth2UserInfo {
    @JsonIgnore
    private final OAuth2Provider provider = OAuth2Provider.GOOGLE;

    @JsonProperty("id")
    private String providerId;

    @JsonProperty("properties")
    private Properties properties;

    @JsonProperty("kakao_account")
    private KakaoAccount kakaoAccount;

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Properties {
        @JsonProperty("nickname")
        private String nickname;

        @JsonProperty("profile_image")
        private String profileImage;

        @JsonProperty("thumbnail_image")
        private String thumbnailImage;
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class KakaoAccount {
        @JsonProperty("profile")
        private Profile profile;

        @JsonProperty("email")
        private String email;

        @JsonProperty("is_email_verified")
        private Boolean isEmailVerified;

        @Getter
        @NoArgsConstructor
        @AllArgsConstructor
        public static class Profile {
            @JsonProperty("nickname")
            private String nickname;

            @JsonProperty("profile_image_url")
            private String profileImageUrl;

            @JsonProperty("thumbnail_image_url")
            private String thumbnailImageUrl;
        }
    }

    public String getEmail() {
        return kakaoAccount != null ? kakaoAccount.getEmail() : null;
    }

    public String getImageUrl() {
        if (kakaoAccount != null && kakaoAccount.getProfile() != null) {
            return kakaoAccount.getProfile().getProfileImageUrl();
        }
        return properties != null ? properties.getProfileImage() : null;
    }
}
