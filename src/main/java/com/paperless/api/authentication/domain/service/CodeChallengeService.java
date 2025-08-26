package com.paperless.api.authentication.domain.service;

import com.paperless.api.core.error.ExceptionCreator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Base64;

import static com.paperless.api.authentication.exception.OAuth2Exception.FAILED_TO_CREATE_CODE_CHALLENGE;

@Service
@Slf4j
@RequiredArgsConstructor
public class CodeChallengeService {
    public String createCodeChallenge(String codeVerifier) {
        try {
            java.security.MessageDigest digest = java.security.MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(codeVerifier.getBytes(java.nio.charset.StandardCharsets.UTF_8));
            return Base64.getUrlEncoder().withoutPadding().encodeToString(hash);
        } catch (Exception e) {
            throw ExceptionCreator.create(FAILED_TO_CREATE_CODE_CHALLENGE, e.getMessage());
        }
    }
}
