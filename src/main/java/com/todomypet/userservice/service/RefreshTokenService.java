package com.todomypet.userservice.service;

import com.todomypet.userservice.dto.TokenResponseDTO;

public interface RefreshTokenService {
    void updateRefreshToken(String userId, String refreshToken);
    String getRefreshToken(String userId);
    TokenResponseDTO reissueToken(String refreshToken);
}
