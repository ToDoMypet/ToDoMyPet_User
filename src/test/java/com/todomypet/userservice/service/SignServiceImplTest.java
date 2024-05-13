package com.todomypet.userservice.service;

import static org.mockito.Mockito.*;

import com.todomypet.userservice.domain.node.User;
import com.todomypet.userservice.dto.SignUpReqDTO;
import com.todomypet.userservice.mapper.UserMapper;
import com.todomypet.userservice.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class SignServiceImplTest {

    SignService signService;
    BCryptPasswordEncoder passwordEncoderMock;
    MailService mailServiceMock;
    UserRepository userRepositoryMock;
    UserMapper userMapperMock;
    NotificationServiceClient notificationServiceClientMock;

    @BeforeEach
    public void beforeEach() {
        userRepositoryMock = mock(UserRepository.class);
        passwordEncoderMock = mock(BCryptPasswordEncoder.class);
        mailServiceMock = mock(MailService.class);
        userMapperMock = mock(UserMapper.class);
        notificationServiceClientMock = mock(NotificationServiceClient.class);
        signService = new SignServiceImpl(userRepositoryMock,
                passwordEncoderMock,
                mailServiceMock,
                userMapperMock,
                notificationServiceClientMock);
    }

    @Test
    void 회원가입() {
        SignUpReqDTO signUpInfo = new SignUpReqDTO("test@example.com",
                "password1234!@@",
                "닉네임",
                "자기소개");
        String testPersonalCode = "AB123456789";
        User testUser =  User.builder()
                .id(UUID.randomUUID().toString())
                .email(signUpInfo.getEmail())
                .password("encodedPassword")
                .profilePicUrl("")
                .nickname(signUpInfo.getNickname())
                .bio(signUpInfo.getBio())
                .createdAt(LocalDateTime.now())
                .deleted(Boolean.FALSE)
                .Protected(Boolean.TRUE)
                .personalCode(testPersonalCode)
                .achCount(0)
                .attendCount(0)
                .collectionCount(0)
                .todoClearCount(0)
                .petEvolveCount(0)
                .petCompleteCount(0)
                .attendContinueCount(1)
                .friendCount(0)
                .lastAttendAt(LocalDate.now().minusDays(1))
                .authority("ROLE_USER")
                .build();


        when(userRepositoryMock.getOneUserByEmail(signUpInfo.getEmail())).thenReturn(Optional.empty());
        when(userRepositoryMock.getOneUserByPersonalCode(testPersonalCode)).thenReturn(Optional.empty());
        when(userRepositoryMock.save(any(User.class))).thenReturn(testUser);

        String savedUserId = signService.signUp(signUpInfo);

        assertNotNull(savedUserId);
        assertEquals(savedUserId, testUser.getId());
    }

    @Test
    void addAdminInfo() {
    }

    @Test
    void 중복_회원_체크() {
        // given
        // when
        // then
    }

    @Test
    void sendCheckEmail() {
    }

    @Test
    void getUserDetailsByEmail() {
    }

    @Test
    void deleteAccount() {
    }

    @Test
    void checkPassword() {
    }

    @Test
    void changePasswordByEmail() {
    }

    @Test
    void logout() {
    }

    @Test
    void loadUserByUsername() {
    }
}