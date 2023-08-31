package com.todomypet.userservice.config.jwt;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@RedisHash(value = "refreshToken")
public class RefreshToken {
    @Id
    private String userId;
    private String refreshToken;

    public static RefreshToken of(String userId, String refreshToken) {
        RefreshToken rt = new RefreshToken();
        rt.userId = userId;
        rt.refreshToken = refreshToken;
        return rt;
    }
}
