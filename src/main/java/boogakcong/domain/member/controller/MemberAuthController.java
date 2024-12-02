package boogakcong.domain.member.controller;

import boogakcong.domain.member.dto.request.MemberAuthLogoutRequest;
import boogakcong.domain.member.dto.request.MemberAuthRefreshRequest;
import boogakcong.domain.member.dto.request.MemberAuthRequest;
import boogakcong.domain.member.dto.request.MemberAuthSignupRequest;
import boogakcong.domain.member.service.MemberAuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MemberAuthController {
    private final MemberAuthService memberAuthService;

    @PostMapping("/api/member/auth/signup")
    public ResponseEntity<?> signup(@RequestBody MemberAuthSignupRequest request) {
        return ResponseEntity.ok(memberAuthService.signup(request));
    }

    @PostMapping("/api/member/auth")
    public ResponseEntity<?> login(@RequestBody MemberAuthRequest request) {
        return ResponseEntity.ok(memberAuthService.login(request));
    }

    @PostMapping("/api/member/auth/refresh")
    public ResponseEntity<?> refresh(@RequestBody MemberAuthRefreshRequest request) {
        return ResponseEntity.ok(memberAuthService.refresh(request));
    }

    @PostMapping("/api/member/auth/logout")
    public ResponseEntity<?> logout(@RequestBody MemberAuthLogoutRequest request) {
        return ResponseEntity.ok(memberAuthService.logout(request));
    }
}
