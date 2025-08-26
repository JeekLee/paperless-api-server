package com.paperless.api.authentication.infrastructure.client;

import com.paperless.api.authentication.infrastructure.client.response.KakaoUserInfo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "kakaoUserProfileClient", url = "${app.oauth2.kakao.user-info-url}")
public interface KakaoUserProfileClient {
    @GetMapping
    KakaoUserInfo getUserInfo(@RequestHeader("Authorization") String bearerToken);
}
