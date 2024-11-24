package boogakcong.domain.member.dto.request;

import boogakcong.domain.member.MemberRole;
import boogakcong.domain.member.entity.Member;

public record MemberCreateRequest(
        String email,
        String password,
        String name,
        String phoneNumber
) {
    public Member toEntity() {
        return Member.builder()
                .email(email)
                .password(password)
                .name(name)
                .role(MemberRole.NORMAL_USER)
                .phoneNumber(phoneNumber)
                .build();
    }
}
