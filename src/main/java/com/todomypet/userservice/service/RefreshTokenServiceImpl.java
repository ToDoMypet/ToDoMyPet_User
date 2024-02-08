package com.todomypet.userservice.service;

import com.todomypet.userservice.config.jwt.JwtTokenProvider;
import com.todomypet.userservice.config.jwt.RefreshToken;
import com.todomypet.userservice.domain.node.User;
import com.todomypet.userservice.dto.TokenResponseDTO;
import com.todomypet.userservice.exception.CustomException;
import com.todomypet.userservice.exception.ErrorCode;
import com.todomypet.userservice.repository.RefreshTokenRepository;
import com.todomypet.userservice.repository.UserRepository;
import io.jsonwebtoken.Jwts;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class RefreshTokenServiceImpl implements RefreshTokenService {

    private final RefreshTokenRepository refreshTokenRepository;
    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final Environment env;

    @Transactional
    @Override
    public void updateRefreshToken(String userId, String refreshToken) {
        User user = userRepository.getOneUserById(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_EXISTS));
        refreshTokenRepository.save(RefreshToken.of(userId, refreshToken));
    }

    @Override
    public String getRefreshToken(String userId) {
        return refreshTokenRepository.findById(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_EXISTS)).getRefreshToken();
    }

    @Override
    public TokenResponseDTO reissueToken(String refreshToken) {
        RefreshToken rtk = refreshTokenRepository.findById(refreshToken)
                .orElseThrow(() -> new CustomException(ErrorCode.INVALID_TOKEN));
        log.info(">>> refresh token : " + refreshToken);

        String userId = rtk.getUserId();

        String authority = userRepository.getOneUserById(userId).orElseThrow(()
                -> new CustomException(ErrorCode.USER_NOT_EXISTS)).getAuthority();
        String newRefreshToken = jwtTokenProvider.createJwtRefreshToken(userId);
        String newAccessToken = jwtTokenProvider.createJwtAccessToken(userId, authority);

        refreshTokenRepository.deleteById(refreshToken);
        refreshTokenRepository.save(RefreshToken.of(userId, newRefreshToken));

        return TokenResponseDTO.builder().refreshToken(newRefreshToken).accessToken(newAccessToken).build();
    }

}
