package boogakcong.domain.member.service;

import boogakcong.domain.member.dto.request.MemberAuthLogoutRequest;
import boogakcong.domain.member.dto.request.MemberAuthRefreshRequest;
import boogakcong.domain.member.dto.request.MemberAuthRequest;
import boogakcong.domain.member.dto.request.MemberAuthSignupRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberAuthService {
    private final MemberService memberService;

    public Object signup(MemberAuthSignupRequest request) {
        return null;
    }

    public Object login(MemberAuthRequest request) {
        return null;
    }

    public Object refresh(MemberAuthRefreshRequest request) {
        return null;
    }

    public Object logout(MemberAuthLogoutRequest request) {
        return null;
    }
}


