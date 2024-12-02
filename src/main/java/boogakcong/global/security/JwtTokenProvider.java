package boogakcong.global.security;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class JwtTokenProvider {
    @Value("${jwt.secret}")
    private final String secretKey;
    @Value("${jwt.token-valid-time}")
    private final long tokenValidTime;
    @Value("${jwt.refresh-token-valid-time}")
    private final long refreshTokenValidTime;
    @Value("${jwt.header}")
    private final String header;
    @Value("${jwt.prefix}")
    private final String prefix;
    @Value("${jwt.refresh-header}")
    private final String refreshHeader;
    @Value("${jwt.refresh-prefix}")
    private final String refreshPrefix;
    @Value("${jwt.issuer}")
    private final String issuer;
    @Value("${jwt.subject}")
    private final String subject;

    public String createToken(String userPk, String role) {
        
        return null;
    }

    public String createRefreshToken(String userPk, String role) {
        return null;
    }

    public String resolveToken(String token) {
        return null;
    }

    public String resolveRefreshToken(String token) {
        return null;
    }

    public boolean validateToken(String token) {
        return false;
    }

    public boolean validateRefreshToken(String token) {
        return false;
    }

    public String getUserPk(String token) {
        return null;
    }

    public String getRole(String token) {
        return null;
    }

    public String getRefreshUserPk(String token) {
        return null;
    }

    public String getRefreshRole(String token) {
        return null;
    }
}
