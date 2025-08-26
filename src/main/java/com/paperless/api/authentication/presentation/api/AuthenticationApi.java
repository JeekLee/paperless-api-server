package com.paperless.api.authentication.presentation.api;

import com.paperless.api.authentication.dto.UserPrincipal;
import com.paperless.api.authentication.exception.OAuth2Exception;
import com.paperless.api.authentication.exception.RefreshTokenException;
import com.paperless.api.authentication.presentation.request.OAuth2LogInRequest;
import com.paperless.api.authentication.presentation.request.OAuth2UrlRequest;
import com.paperless.api.authentication.presentation.response.LoginResponse;
import com.paperless.api.authentication.presentation.response.OAuth2UrlResponse;
import com.paperless.api.core.document.aspect.annotation.ApiErrorCodeExamples;
import com.paperless.api.core.document.aspect.annotation.EnumExceptionMapping;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Tag(name = "Authentication", description = "사용자 인증 API")
@RequestMapping("/auth")
@Validated
public interface AuthenticationApi {
    @Operation(
            summary = "Generate OAuth2 authorization URL",
            description = """
                     ## ✨ 주요 기능
                     
                     OAuth2 인증 프로세스를 시작하기 위한 인증 URL을 생성합니다.
                     
                     PKCE 방식을 사용하며, 클라이언트에서 생성한 `code verifier`를 이용해\s
                     `code challenge`를 생성하여 인증 URL에 포함시킵니다.
                     
                     생성된 URL로 사용자를 리디렉션하면 OAuth2 제공자의 인증 페이지로 이동합니다.
                     """
    )
    @PostMapping("/oauth2/url")
    @ApiErrorCodeExamples(
            @EnumExceptionMapping(enumClass = OAuth2Exception.class,
                    values = {"PROVIDER_NOT_SUPPORTED", "FAILED_TO_CREATE_CODE_CHALLENGE"})
    )
    ResponseEntity<OAuth2UrlResponse> oAuth2SignIn(@Valid @RequestBody OAuth2UrlRequest request);

    @Operation(
            summary = "Complete OAuth2 login process",
            description = """
                    ## ✨ 주요 기능
                    
                    OAuth2 인증 프로세스를 완료하고 로그인을 처리합니다.
                    
                    OAuth2 제공자로부터 받은 authorization `code`와 `state`를 검증한 후,\s
                    액세스 토큰을 획득하여 사용자 프로필 정보를 가져와 로그인 또는 회원가입을 진행합니다.
                    
                    회원가입 시, 프로필 이미지가 존재하는 경우 해당 프로필 이미지로 설정합니다.
                    프로필 이미지가 존재하지 않는 경우, null로 저장됩니다.
                    
                    PKCE 흐름에서 `state`는 CSRF 공격 방지와 세션 유지를 위해 사용됩니다.
                    """
    )
    @PostMapping("/oauth2/login")
    @ApiErrorCodeExamples(
            @EnumExceptionMapping(enumClass = OAuth2Exception.class,
                    values = {"OAUTH2_CONTEXT_NOT_FOUND", "FAILED_TO_GET_ACCESS_TOKEN", "FAILED_TO_GET_USER_PROFILE"})
    )
    ResponseEntity<LoginResponse> oAuth2LogIn(@Valid @RequestBody OAuth2LogInRequest request);

    @Operation(
            summary = "Log out user and clear authentication",
            description = """
               ## ✨ 주요 기능
               
               사용자 로그아웃을 처리하고 인증 정보를 정리합니다.
               
               현재 로그인된 사용자의 세션을 종료하고, 저장된 refresh token을 무효화합니다.
               
               또한 클라이언트의 인증 쿠키(access token, refresh token)를 삭제하여\s
               완전한 로그아웃을 수행합니다.
               
               로그아웃 후에는 보호된 리소스에 접근하기 위해 다시 로그인이 필요합니다.
               """
    )
    @PostMapping("/logout")
    @PreAuthorize("isAuthenticated()")
    ResponseEntity<Void> logOut(@AuthenticationPrincipal UserPrincipal userPrincipal);

    @Operation(
            summary = "Refresh access token",
            description = """
                    ## ✨ 주요 기능
                    
                    만료된 액세스 토큰을 갱신합니다.
                    
                    유효한 refresh token을 사용하여 새로운 access token을 발급받습니다.
                    
                    새로운 토큰들은 응답 쿠키에 자동으로 설정됩니다.
                    """
    )
    @PostMapping("/token/refresh")
    @ApiErrorCodeExamples(
            @EnumExceptionMapping(enumClass = RefreshTokenException.class,
                    values = {"REFRESH_TOKEN_NOT_FOUND", "REFRESH_TOKEN_INVALID", "REFRESH_TOKEN_EXPIRED", "REFRESH_TOKEN_NOT_MATCH"})
    )
    ResponseEntity<Void> refreshAuthenticationToken(HttpServletRequest request);
}