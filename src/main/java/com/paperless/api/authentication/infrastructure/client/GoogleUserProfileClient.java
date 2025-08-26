package com.paperless.api.authentication.infrastructure.client;

import com.paperless.api.authentication.infrastructure.client.response.GoogleUserInfo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "googleUserProfileClient", url = "${app.oauth2.google.user-info-url}")
public interface GoogleUserProfileClient {
    @GetMapping
    GoogleUserInfo getUserInfo(@RequestHeader("Authorization") String bearerToken);
}
