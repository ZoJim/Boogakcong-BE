package boogakcong.domain.member.dto.response;

public record TokenResponse(
        String accessToken,
        String refreshToken
) {
}
