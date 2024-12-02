package boogakcong.domain.member.controller;

import boogakcong.domain.member.dto.request.MemberAuthLogoutRequest;
import boogakcong.domain.member.dto.request.MemberAuthRefreshRequest;
import boogakcong.domain.member.dto.request.MemberLoginRequest;
import boogakcong.domain.member.dto.request.MemberAuthSignupRequest;
import boogakcong.domain.member.service.MemberAuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/member/auth")
@RequiredArgsConstructor
public class MemberAuthController {
    private final MemberAuthService memberAuthService;

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@Validated @RequestBody MemberAuthSignupRequest request) {
        return ResponseEntity.ok(memberAuthService.signup(request));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody MemberLoginRequest request) {
        return ResponseEntity.ok(memberAuthService.login(request));
    }

    @PostMapping("/refresh")
    public ResponseEntity<?> refresh(@RequestBody MemberAuthRefreshRequest request) {
        return ResponseEntity.ok(memberAuthService.refresh(request));
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(@RequestBody MemberAuthLogoutRequest request) {
        memberAuthService.logout(request);
        return ResponseEntity.ok().build();
    }
}
