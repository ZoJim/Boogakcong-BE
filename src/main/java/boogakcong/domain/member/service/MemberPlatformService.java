package boogakcong.domain.member.service;

import boogakcong.domain.member.dto.request.MemberAuthSignupRequest;
import boogakcong.domain.member.dto.response.MemberResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.beans.Encoder;

@RequiredArgsConstructor
public class MemberPlatformService {
    private final MemberService memberService;


    public MemberResponse getMemberById(Long id) {
        return memberService.getMemberById(id).fromEntity();
    }


}
