package com.paperless.api.notification.presentation.api;

import com.paperless.api.authentication.dto.UserPrincipal;
import com.paperless.api.notification.presentation.response.NotificationBoxResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Tag(name = "MyNotification", description = "내 알림 API")
@RequestMapping("/notification")
public interface MyNotificationApi {
    @Operation(
            summary = "Get my notification box",
            description = """
                    ## ✨ 주요 기능
                   
                    내 알림 보관함을 조회합니다.
                   
                    페이지네이션 정보를 전달하면, 페이지 객체로 래핑된 알림 정보와 읽지 않은 알림 수량을 반환합니다.
                   
                    인증된 사용자만 접근 가능합니다.
                    """
    )
    @GetMapping(path = "/me/box")
    @PreAuthorize("isAuthenticated()")
    ResponseEntity<NotificationBoxResponse> getBox(@AuthenticationPrincipal UserPrincipal userPrincipal, @ParameterObject Pageable pageable);

    @Operation(
            summary = "Read notification",
            description = """
                    ## ✨ 주요 기능

                    내 알림을 읽음으로 표시합니다.

                    인증된 사용자만 접근 가능합니다.
                    """
    )
    @PreAuthorize("isAuthenticated()")
    @GetMapping(path = "/me/read/{notificationId}")
    ResponseEntity<Void> readNotification(
            @AuthenticationPrincipal UserPrincipal userPrincipal,
            @PathVariable(name = "notificationId") Long notificationId
    );
}
