package com.todomypet.userservice.service;

import com.todomypet.userservice.domain.node.Achievement;
import com.todomypet.userservice.domain.node.AchievementType;
import com.todomypet.userservice.dto.FeignClientResDTO;
import com.todomypet.userservice.dto.achievement.AchieveReqDTO;
import com.todomypet.userservice.dto.openFeign.SendNotificationByActionReqDTO;
import com.todomypet.userservice.repository.AchieveRepository;
import com.todomypet.userservice.repository.AchievementRepository;
import com.todomypet.userservice.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.mockito.Mockito.*;

class AchievementServiceImplTest {
    AchievementService achievementService;
    AchievementRepository achievementRepositoryMock;
    AchieveRepository achieveRepositoryMock;
    UserRepository userRepositoryMock;
    NotificationServiceClient notificationServiceClientMock;

    @BeforeEach
    public void beforeEach() {
        userRepositoryMock = mock(UserRepository.class);
        achievementRepositoryMock = mock(AchievementRepository.class);
        achieveRepositoryMock = mock(AchieveRepository.class);
        notificationServiceClientMock = mock(NotificationServiceClient.class);
        achievementService = new AchievementServiceImpl(achievementRepositoryMock,
                achieveRepositoryMock,
                userRepositoryMock,
                notificationServiceClientMock);
    }

    @Test
    void given_satisfyAchievementCondition_when_achieve_then_returnUserId() {
        // Given
        String userId = "123abced-1234-1234-1234-1234abcd123";
        String achId = "01";
        AchieveReqDTO achieveReqDTO = AchieveReqDTO.builder().type(AchievementType.ACHIEVE).condition(10).build();
        Achievement achievement = Achievement.builder().id(achId).achType(AchievementType.ACHIEVE).build();
        LocalDateTime achievedAt = LocalDateTime.parse(LocalDateTime.now()
                .format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")));
        when(achievementRepositoryMock.isSatisfyAchievementCondition(achieveReqDTO.getType(), achieveReqDTO.getCondition()))
                .thenReturn(achievement);
        when(achieveRepositoryMock.existsAchieveBetweenUserAndAchievement(userId, achievement.getId()))
                .thenReturn(null);

        when(notificationServiceClientMock.sendNotificationByAction(anyString(), any(SendNotificationByActionReqDTO.class)))
                .thenReturn(FeignClientResDTO.<String>builder().code("001").data("01").build());

        // When
        String result = achievementService.achieve(userId, achieveReqDTO);

        // Then
        verify(achievementRepositoryMock).isSatisfyAchievementCondition(achieveReqDTO.getType(), achieveReqDTO.getCondition());
        verify(achieveRepositoryMock).existsAchieveBetweenUserAndAchievement(userId, achievement.getId());
        verify(achieveRepositoryMock).createAchieveBetweenUserAndAchievement(userId, achievement.getId(), achievedAt);
        verify(notificationServiceClientMock).sendNotificationByAction(anyString(), any(SendNotificationByActionReqDTO.class));
        verify(userRepositoryMock).increaseAchieveCount(userId);
        verify(userRepositoryMock).createAvailableByAchieveCondition(userId);
    }
}
