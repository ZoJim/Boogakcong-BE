package boogakcong.domain.member.service;

import boogakcong.domain.member.dto.request.MemberAuthLogoutRequest;
import boogakcong.domain.member.dto.request.MemberAuthRefreshRequest;
import boogakcong.domain.member.dto.request.MemberLoginRequest;
import boogakcong.domain.member.dto.request.MemberAuthSignupRequest;
import boogakcong.domain.member.dto.response.TokenResponse;
import boogakcong.domain.member.entity.Member;
import boogakcong.global.exception.BusinessError;
import boogakcong.global.exception.BusinessException;
import boogakcong.global.security.JwtTokenProvider;
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
    private final JwtTokenProvider tokenProvider;

    @Transactional
    public Long signup(MemberAuthSignupRequest request) {
        memberService.validateDuplicateEmail(request.email());
        return memberService.createMember(request.toEntity(passwordEncoder.encode(request.password())));
    }

    @Transactional
    public Long confirmCaffeManager(Long memberId) {
        Member member = memberService.getMemberById(memberId);
        member.confirmCaffeManager();
        memberService.updateMember(member);
        return member.getId();
    }

    public TokenResponse login(MemberLoginRequest request) {
        Member member = memberService.getMemberByEmail(request.email());
        if (!passwordEncoder.matches(request.password(), member.getPassword())) {
            throw new BusinessException(BusinessError.MEMBER_LOGIN_FAILED);
        }

        return TokenResponse.builder()
                .accessToken(tokenProvider.createToken(
                                member.getName(),
                                member.getId(),
                                member.getRole().toString()
                        )
                )
                .build();
    }

    public TokenResponse refresh(MemberAuthRefreshRequest request) {
        return null;
    }

    public void logout(MemberAuthLogoutRequest request) {

    }
}


