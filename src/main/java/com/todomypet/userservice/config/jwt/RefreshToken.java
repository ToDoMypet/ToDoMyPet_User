package com.todomypet.userservice.config.jwt;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@RedisHash(value = "refreshToken", timeToLive = 60*60*24*3)
public class RefreshToken {
    @Id
    private String refreshToken;
    private String userId;

    public static RefreshToken of(String userId, String refreshToken) {
        RefreshToken rt = new RefreshToken();
        rt.userId = userId;
        rt.refreshToken = refreshToken;
        return rt;
    }
}
