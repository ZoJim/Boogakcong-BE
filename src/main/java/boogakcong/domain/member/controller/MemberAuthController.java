package boogakcong.domain.member.controller;

import boogakcong.domain.member.dto.request.MemberAuthLogoutRequest;
import boogakcong.domain.member.dto.request.MemberAuthRefreshRequest;
import boogakcong.domain.member.dto.request.MemberLoginRequest;
import boogakcong.domain.member.dto.request.MemberAuthSignupRequest;
import boogakcong.domain.member.dto.response.TokenResponse;
import boogakcong.domain.member.service.MemberAuthService;
import boogakcong.global.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/member/auth")
@RequiredArgsConstructor
public class MemberAuthController {
    private final MemberAuthService memberAuthService;

    @PostMapping("/signup")
    public ResponseEntity<TokenResponse> signup(@Validated @RequestBody MemberAuthSignupRequest request) {
        return ResponseEntity.ok(memberAuthService.signup(request));
    }

    @PostMapping("/login")
    public ResponseEntity<TokenResponse> login(@RequestBody MemberLoginRequest request) {
        return ResponseEntity.ok(memberAuthService.login(request));
    }

    @PostMapping("/refresh")
    public ResponseEntity<?> refresh(@RequestBody MemberAuthRefreshRequest request) {
        return ResponseEntity.ok(memberAuthService.refresh(request));
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(@RequestBody MemberAuthLogoutRequest request) {
        return ResponseEntity.ok().build();
    }

    @Secured("ROLE_ADMIN")
    @PostMapping("/confirm-cafe-owner")
    public ResponseEntity<?> confirmCaffeOwner(
            @AuthenticationPrincipal UserDetailsImpl userDetails, @RequestParam(name = "memberId") Long memberId
    ) {
        return ResponseEntity.ok(memberAuthService.confirmCafeOwner(memberId));
    }

    @Secured("ROLE_ADMIN")
    @PostMapping("/confirm-community-manager")
    public ResponseEntity<?> confirmCommunityManager(
            @AuthenticationPrincipal UserDetailsImpl userDetails, @RequestParam(name = "memberId") Long memberId
    ) {
        return ResponseEntity.ok(memberAuthService.confirmCommunityManager(memberId));
    }
}
