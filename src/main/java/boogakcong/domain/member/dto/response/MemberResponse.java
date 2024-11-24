package boogakcong.domain.member.dto.response;

import lombok.Builder;

@Builder
public record MemberResponse(
        Long id,
        String role,
        String email
) {
}
