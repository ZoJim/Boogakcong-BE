package boogakcong.domain.member.dto.request;

import boogakcong.domain.member.MemberRole;
import boogakcong.domain.member.entity.Member;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record MemberAuthSignupRequest(
        @Email
        @NotNull
        String email,
        @NotNull
        @Size(min = 8, max = 20, message = "비밀번호는 8자 이상 20자 이하로 입력해주세요.")
        String password,
        @NotNull
        @Size(min = 2, max = 20)
        String name,
        @NotNull
        String phoneNumber
) {
    public Member toEntity(String encodedPassword) {
        return Member.builder()
                .email(email)
                .password(encodedPassword)
                .name(name)
                .phoneNumber(phoneNumber)
                .role(MemberRole.ROLE_NORMAL_USER)
                .build();
    }
}
