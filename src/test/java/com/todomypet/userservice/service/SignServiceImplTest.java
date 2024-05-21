package com.todomypet.userservice.service;

import static org.mockito.Mockito.*;

import com.todomypet.userservice.domain.node.User;
import com.todomypet.userservice.dto.DuplicationCheckResDTO;
import com.todomypet.userservice.dto.SignUpReqDTO;
import com.todomypet.userservice.exception.CustomException;
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
    void given_userInfo_when_signUp_then_returnUserId() {
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
    void given_userExistsAndNotDeleted_when_duplicationCheck_then_returnDuplicationIsTrueAndDeletedIsFalse() {
        String email = "test@example.com";
        User user = User.builder().deleted(false).build();

        when(userRepositoryMock.getUserCountByEmail(email)).thenReturn(1);
        when(userRepositoryMock.getOneUserByEmail(email)).thenReturn(Optional.of(user));

        DuplicationCheckResDTO result = signService.duplicationCheck(email);

        assertTrue(result.getDuplicationOrNot());
        assertFalse(user.getDeleted());
    }

    @Test
    void given_userNotExists_when_duplicationCheck_then_returnDuplicationIsFalseAndDeletedIsNull() {
        String email = "test@example.com";

        when(userRepositoryMock.getUserCountByEmail(email)).thenReturn(0);

        DuplicationCheckResDTO result = signService.duplicationCheck(email);

        assertFalse(result.getDuplicationOrNot());
        assertNull(result.getDeletedOrNot());
    }

    @Test
    void given_userExistAndDeleted_when_duplicationCheck_then_returnDuplicationIsTrueAndDeleteIsTrue() {
        String email = "test@example.com";
        User user = User.builder().deleted(true).build();

        when(userRepositoryMock.getUserCountByEmail(email)).thenReturn(1);
        when(userRepositoryMock.getOneUserByEmail(email)).thenReturn(Optional.of(user));

        DuplicationCheckResDTO result = signService.duplicationCheck(email);

        assertTrue(result.getDuplicationOrNot());
        assertTrue(user.getDeleted());
    }

    @Test
    public void given_userExistsButNotFound_when_duplicationCheck_then_throwsException() {
        String email = "test@example.com";

        when(userRepositoryMock.getUserCountByEmail(email)).thenReturn(1);
        when(userRepositoryMock.getOneUserByEmail(email)).thenReturn(Optional.empty());

        CustomException exception = assertThrows(CustomException.class, () -> {
            signService.duplicationCheck(email);
        });

        assertEquals("U002", exception.getErrorCode().getCode());
    }

    @Test
    public void given_correctUserIdAndPassword_when_checkPassword_then_returnTrue() {
        String userId = "123abced-1234-1234-1234-1234abcd123";
        String rawPassword = "password123";
        String encodedPassword = "$2a$10$DowJ9w8BzOZiWJbO3W8A1e.Cj/U2SDhZpLcyQO7akZle4yE4yyT5y";

        when(userRepositoryMock.getPasswordByUserId(userId)).thenReturn(encodedPassword);
        when(passwordEncoderMock.matches(rawPassword, encodedPassword)).thenReturn(true);

        boolean result = signService.checkPassword(userId, rawPassword);

        assertTrue(result);
    }

    @Test
    public void given_incorrectUserIdAndPassword_when_checkPassword_then_returnFalse() {
        String userId = "123abced-1234-1234-1234-1234abcd123";
        String rawPassword = "wrongPassword";
        String encodedPassword = "$2a$10$DowJ9w8BzOZiWJbO3W8A1e.Cj/U2SDhZpLcyQO7akZle4yE4yyT5y";

        when(userRepositoryMock.getPasswordByUserId(userId)).thenReturn(encodedPassword);
        when(passwordEncoderMock.matches(rawPassword, encodedPassword)).thenReturn(false);

        boolean result = signService.checkPassword(userId, rawPassword);

        assertFalse(result);
    }
}