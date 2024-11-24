package boogakcong.domain.member.controller;

import boogakcong.domain.member.dto.request.MemberCreateRequest;
import boogakcong.domain.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
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

    @PostMapping
    public ResponseEntity<?> createMember(@RequestBody MemberCreateRequest request) {
        return ResponseEntity.ok(memberService.createMember(request));
    }
}
