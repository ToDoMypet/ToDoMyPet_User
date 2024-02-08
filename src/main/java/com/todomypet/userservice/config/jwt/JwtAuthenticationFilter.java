package com.todomypet.userservice.config.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.todomypet.userservice.dto.GetUserDetailsDTO;
import com.todomypet.userservice.dto.LoginRequestDTO;
import com.todomypet.userservice.service.SignService;
import com.todomypet.userservice.service.RefreshTokenService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;
import java.util.ArrayList;

@RequiredArgsConstructor
@Slf4j
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;
    private final SignService signService;
    private final RefreshTokenService refreshTokenService;
    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request,
                                                HttpServletResponse response) throws AuthenticationException {
        log.info("JwtAuthenticationFilter: 진입");

        ObjectMapper om = new ObjectMapper();
        LoginRequestDTO loginRequestDTO = null;
        log.info(request.toString());

        try {
            loginRequestDTO = om.readValue(request.getInputStream(), LoginRequestDTO.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(
                        loginRequestDTO.getEmail(),
                        loginRequestDTO.getPassword(),
                        new ArrayList<>()
                );

        return authenticationManager.authenticate(authenticationToken);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        log.info("JwtAuthenticationFilter: successfulAuthentication checking");
        String userEmail = ((User)authResult.getPrincipal()).getUsername();
        GetUserDetailsDTO userDetailsDTO = signService.getUserDetailsByEmail(userEmail);

        String userId = userDetailsDTO.getId();
        String authority = userDetailsDTO.getAuthority();
        String accessToken = jwtTokenProvider.createJwtAccessToken(userId, authority);
        String refreshToken = jwtTokenProvider.createJwtRefreshToken(userId);

        refreshTokenService.updateRefreshToken(userId, refreshToken);
        response.addHeader("refreshToken", refreshToken);
        response.addHeader("accessToken", accessToken);
    }
}
