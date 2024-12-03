package boogakcong.global.security;

import boogakcong.domain.member.dto.response.TokenResponse;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Component
public class JwtTokenProvider {

    @Value("${jwt.secret}")
    private String secretKey;

    private final static long ACCESS_TOKEN_VALIDITY_SECONDS = 5 * 60 * 60;
    private final static long REFRESH_TOKEN_VALIDITY_SECONDS = 10 * 60 * 60;

    public TokenResponse createTokenResponse(String username, Long userId, String role) {
        return TokenResponse.builder()
                .accessToken(createToken(username, userId, role, ACCESS_TOKEN_VALIDITY_SECONDS))
                .refreshToken(createToken(username, userId, role, REFRESH_TOKEN_VALIDITY_SECONDS))
                .build();

    }

    public String createToken(String username, Long userId, String role, Long period) {
        return Jwts.builder()
                .setSubject(String.valueOf(userId))
                .claim("roles", List.of(role))
                .claim("userName", username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + period * 1000))
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

    public String getUsername(String token) {
        return Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(token)
                .getBody()
                .get("userName", String.class);
    }

    public String getRole(String token) {
        return ((List<String>) Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(token)
                .getBody()
                .get("roles")).get(0);
    }

    public String getUserId(String token) {
        return Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

}
