package com.paperless.api.member.presentation.api;

import com.paperless.api.authentication.dto.UserPrincipal;
import com.paperless.api.member.dto.MemberInfo;
import com.paperless.api.member.presentation.request.UpdateImageRequest;
import com.paperless.api.member.presentation.request.UpdateNicknameRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Tag(name = "MyMemberInfo", description = "내 사용자 기본 정보 API")
@RequestMapping("/member")
@Validated
public interface MyMemberInfoApi {
    @Operation(
            summary = "Get my member information",
            description = """
                    ## ✨ 주요 기능
                   
                    내 회원 정보를 조회합니다.
                   
                    로그인한 사용자의 프로필 정보, 닉네임, 프로필 이미지 등 상세한 회원 데이터를 반환합니다.
                   
                    인증된 사용자만 접근 가능합니다.
                    """
    )
    @GetMapping(path = "/me/info")
    @PreAuthorize("isAuthenticated()")
    ResponseEntity<MemberInfo> getInfo(@AuthenticationPrincipal UserPrincipal userPrincipal);

    @Operation(
            summary = "Update my member image",
            description = """
                   ## ✨ 주요 기능
                   
                   내 프로필 이미지를 업데이트합니다.
                   
                   multipart form 데이터로 새로운 프로필 이미지 파일을 업로드합니다.
                   
                   이미지는 검증 및 처리 과정을 거쳐 회원 프로필에 반영됩니다.
                   
                   ## ✅ 이미지 검증 항목
                   
                   - allowedTypes = {"image/jpeg", "image/jpg", "image/png"},
                   - maxSizeBytes = 5 * 1024 * 1024
                   """
    )
    @PatchMapping(path = "/me/info/image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("isAuthenticated()")
    ResponseEntity<MemberInfo> updateImage(@AuthenticationPrincipal UserPrincipal userPrincipal, @Valid @ModelAttribute UpdateImageRequest request);

    @Operation(
            summary = "Update my member image",
            description = """
                    ## ✨ 주요 기능
           
                    내 닉네임을 업데이트합니다.
           
                    새로운 닉네임은 `#`와 함께 랜덤한 4자리 숫자가 부여됩니다.
                    
                    새로운 닉네임은 유효성 검사를 통과해야 하며, 중복 확인 후 회원 정보에 반영됩니다.
         
                    변경된 회원 정보가 응답으로 반환됩니다.
                    """
    )
    @PatchMapping("/me/info/nickname")
    @PreAuthorize("isAuthenticated()")
    ResponseEntity<MemberInfo> updateNickname(@AuthenticationPrincipal UserPrincipal userPrincipal, @Valid @RequestBody UpdateNicknameRequest request);

}
