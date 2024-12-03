package boogakcong.domain.member.dto.request;

public record MemberAuthLogoutRequest(
        String accessToken,
        String refreshToken
) {
}
