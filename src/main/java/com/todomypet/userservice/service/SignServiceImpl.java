package com.todomypet.userservice.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.todomypet.userservice.config.jwt.JwtTokenProvider;
import com.todomypet.userservice.domain.node.User;
import com.todomypet.userservice.dto.GetUserDetailsDTO;
import com.todomypet.userservice.dto.SignUpReqDTO;
import com.todomypet.userservice.dto.TokenResponseDTO;
import com.todomypet.userservice.exception.CustomException;
import com.todomypet.userservice.exception.ErrorCode;
import com.todomypet.userservice.mapper.UserMapper;
import com.todomypet.userservice.repository.RefreshTokenRepository;
import com.todomypet.userservice.repository.UserRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.constraints.Null;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Random;

@Service
@RequiredArgsConstructor
@Slf4j
public class SignServiceImpl implements SignService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final MailService mailService;
    private final UserMapper userMapper;

    @Override
    @Transactional
    public String signUp(SignUpReqDTO signUpInfo) {
        if (userRepository.getOneUserByEmail(signUpInfo.getEmail()).isPresent()) {
            throw new CustomException(ErrorCode.EXISTS_EMAIL);
        }
        StringBuilder personalCode = new StringBuilder();
        Random rnd = new Random();

        while (true) {
            for (int i = 0; i < 2; i++) {
                personalCode.append((char)(rnd.nextInt(26) + 65));
            }
            for (int i = 0; i < 9; i++) {
                personalCode.append(rnd.nextInt(10));
            }

            if (userRepository.getOneUserByPersonalCode(personalCode.toString()).isEmpty()) {
                break;
            }
        }

        User user = User.builder().email(signUpInfo.getEmail())
                .password(passwordEncoder.encode(signUpInfo.getPassword()))
                .profilePicUrl("")
                .nickname(signUpInfo.getNickname())
                .bio(signUpInfo.getBio())
                .createdAt(LocalDateTime.parse(LocalDateTime.now()
                        .format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss"))))
                .deleted(Boolean.FALSE)
                .Protected(Boolean.FALSE)
                .personalCode(personalCode.toString())
                .achCount(0)
                .attendCount(0)
                .collectionCount(0)
                .petAcquireCount(0)
                .petEvolveCount(0)
                .petCompleteCount(0)
                .attendContinueCount(0)
                .friendCount(0)
                .build();
        return userRepository.save(user).getId();
    }

    @Override
    public Boolean duplicationCheck(String checkedEmail) {
        if (userRepository.getUserCountByEmail(checkedEmail) <= 0) {
            return Boolean.TRUE;
        };
        return Boolean.FALSE;
    }

    @Override
    public String sendCheckEmail(String receiveEmail) throws Exception {
        return mailService.sendMail(receiveEmail);
    }

    @Override
    public GetUserDetailsDTO getUserDetailsByEmail(String email) {
        User user = userRepository.getOneUserByEmail(email).orElseThrow();
        GetUserDetailsDTO getUserDetailsDTO = userMapper.userToGetUserDetailsDTO(user);
        return getUserDetailsDTO;
    }

    @Override
    public void deleteAccount(String userId) {
        User user = userRepository.getOneUserById(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_EXISTS));

        if (user.getDeleted()) {
            throw new CustomException(ErrorCode.DELETED_USER);
        }

        LocalDateTime deletedAt = LocalDateTime.parse(LocalDateTime.now()
                .format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")));

        userRepository.deleteAccount(userId, deletedAt);
    }

    @Override
    public boolean checkPassword(String userId, String password) {
        log.info(">>> 비밀번호 확인 진입: " + userId);
        String pw = userRepository.getPasswordByUserId(userId);
        return passwordEncoder.matches(password, pw);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws CustomException {
        User user = userRepository.getOneUserByEmail(username).orElseThrow(() ->
                new CustomException(ErrorCode.USER_NOT_EXISTS));
        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(),
                true, true, true, true, new ArrayList<>());
    }
}
