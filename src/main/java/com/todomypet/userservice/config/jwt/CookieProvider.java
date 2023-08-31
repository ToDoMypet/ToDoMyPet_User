package com.todomypet.userservice.config.jwt;

import jakarta.servlet.http.Cookie;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class CookieProvider {

    @Value("${token.refresh_token_expiration_time}")
    private static long REFRESH_TOKEN_EXPIRED_TIME;

    public static Cookie of(String refreshToken) {
        Cookie cookie = new Cookie("refreshToken", refreshToken);
        cookie.setHttpOnly(true);
        cookie.setSecure(true);
        cookie.setPath("/");
        cookie.setMaxAge((int)REFRESH_TOKEN_EXPIRED_TIME);
        return cookie;
    }
}
