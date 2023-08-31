package com.todomypet.userservice.service;

public interface RefreshTokenService {
    void updateRefreshToken(String userId, String refreshToken);
}
