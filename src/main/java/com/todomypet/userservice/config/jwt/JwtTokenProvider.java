package com.todomypet.userservice.config.jwt;

import com.todomypet.userservice.dto.TokenResponseDTO;
import com.todomypet.userservice.exception.CustomException;
import com.todomypet.userservice.exception.ErrorCode;
import com.todomypet.userservice.service.RefreshTokenService;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@Slf4j
public class JwtTokenProvider {

    @Value("${token.access_token_expiration_time}")
    private long ACCESS_TOKEN_EXPIRED_TIME;

    @Value("${token.refresh_token_expiration_time}")
    private long REFRESH_TOKEN_EXPIRED_TIME;

    @Value("${token.secret}")
    private String SECRET;

    public String createJwtAccessToken(String userId, String authority) {
        return Jwts.builder()
                .setSubject(userId)
                .claim("auth", authority)
                .setExpiration(new Date(System.currentTimeMillis() + ACCESS_TOKEN_EXPIRED_TIME))
                .signWith(SignatureAlgorithm.HS512, SECRET)
                .compact();
    }

    public String createJwtRefreshToken(String userId) {
        return Jwts.builder()
                .setSubject(userId)
                .setExpiration(new Date(System.currentTimeMillis() + REFRESH_TOKEN_EXPIRED_TIME))
                .signWith(SignatureAlgorithm.HS512, SECRET)
                .compact();
    }
}
