package boogakcong.domain.member.controller;

import boogakcong.domain.member.service.MemberService;
import boogakcong.global.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/member")
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;

    @GetMapping
    public ResponseEntity<?> getMember(@RequestParam(name = "id") Long id) {
        return ResponseEntity.ok(memberService.getMemberById(id));
    }

    @GetMapping("/me")
    @Secured({"ROLE_NORMAL_USER", "ROLE_ADMIN", "ROLE_CAFE_OWNER", "ROLE_COMMUNITY_MANAGER"})
    public ResponseEntity<MemberService.MyInfoResponse> getMyInfo(
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        return ResponseEntity.ok(memberService.getMyInfo(userDetails));
    }
}
