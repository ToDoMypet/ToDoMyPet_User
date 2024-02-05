package com.todomypet.userservice.config;

import com.todomypet.userservice.config.jwt.JwtAuthenticationFilter;
import com.todomypet.userservice.config.jwt.JwtTokenProvider;
import com.todomypet.userservice.exception.CustomException;
import com.todomypet.userservice.service.SignService;
import com.todomypet.userservice.service.RefreshTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final BCryptPasswordEncoder passwordEncoder;
    private final SignService signService;
    private final RefreshTokenService refreshTokenService;
    private final JwtTokenProvider jwtTokenProvider;

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)
            throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .formLogin().disable()
                .httpBasic().disable()
//                .authorizeHttpRequests(requests -> requests.requestMatchers("/admin/**").hasRole("ADMIN"))
                .addFilter(new JwtAuthenticationFilter(
                        authenticationManager(http.getSharedObject(AuthenticationConfiguration.class)),
                        signService, refreshTokenService, jwtTokenProvider)
                );

        return http.build();
    }

    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(signService).passwordEncoder(passwordEncoder);
    }
}
