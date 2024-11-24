package boogakcong.domain.member.service;

import boogakcong.domain.member.dto.request.MemberCreateRequest;
import boogakcong.domain.member.dto.response.MemberResponse;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class MemberPlatformService {
    private final MemberService memberService;

    public MemberResponse getMemberById(Long id) {
        return memberService.getMemberById(id).fromEntity();
    }

    public Long createMember(MemberCreateRequest request) {
        return memberService.createMember(request);
    }
}
