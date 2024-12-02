package boogakcong.domain.member.service;

import boogakcong.domain.member.dto.request.MemberAuthLogoutRequest;
import boogakcong.domain.member.dto.request.MemberAuthRefreshRequest;
import boogakcong.domain.member.dto.request.MemberLoginRequest;
import boogakcong.domain.member.dto.request.MemberAuthSignupRequest;
import boogakcong.domain.member.dto.response.TokenResponse;
import boogakcong.domain.member.entity.Member;
import boogakcong.global.exception.BusinessError;
import boogakcong.global.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberAuthService {
    private final MemberService memberService;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public Long signup(MemberAuthSignupRequest request) {
        memberService.validateDuplicateEmail(request.email());
        return memberService.createMember(request.toEntity(passwordEncoder.encode(request.password())));
    }

    public Long login(MemberLoginRequest request) {
        Member memberByEmail = memberService.getMemberByEmail(request.email());
        if (!passwordEncoder.matches(request.password(), memberByEmail.getPassword())) {
            throw new BusinessException(BusinessError.MEMBER_LOGIN_FAILED);
        }

        return memberByEmail.getId();
    }

    public TokenResponse refresh(MemberAuthRefreshRequest request) {
        return null;
    }

    public void logout(MemberAuthLogoutRequest request) {

    }
}


