package com.todomypet.userservice.service;

import com.todomypet.userservice.domain.node.Achievement;
import com.todomypet.userservice.domain.node.AchievementType;
import com.todomypet.userservice.domain.node.User;
import com.todomypet.userservice.domain.relationship.Achieve;
import com.todomypet.userservice.dto.FeignClientResDTO;
import com.todomypet.userservice.dto.achievement.*;
import com.todomypet.userservice.dto.openFeign.NotificationType;
import com.todomypet.userservice.dto.openFeign.SendNotificationByActionReqDTO;
import com.todomypet.userservice.exception.CustomException;
import com.todomypet.userservice.exception.ErrorCode;
import com.todomypet.userservice.repository.AchieveRepository;
import com.todomypet.userservice.repository.AchievementRepository;
import com.todomypet.userservice.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class AchievementServiceImpl implements AchievementService {

    private final AchievementRepository achievementRepository;
    private final AchieveRepository achieveRepository;
    private final UserRepository userRepository;
    private final NotificationServiceClient notificationServiceClient;

    @Transactional
    @Override
    public void addAchievement(AddAchievementReqDTOList req) {
        for (AddAchievementReqDTO addAchievementReqDTO : req.getAchievementList()) {
            Achievement a = Achievement.builder().id(addAchievementReqDTO.getId())
                    .achName(addAchievementReqDTO.getAchName())
                    .achType(addAchievementReqDTO.getAchType())
                    .achDiff(addAchievementReqDTO.getAchDiff())
                    .achCondition(addAchievementReqDTO.getAchCondition())
                    .achAdvice(addAchievementReqDTO.getAchAdvice())
                    .achDescribe(addAchievementReqDTO.getAchDescribe())
                    .build();
            achievementRepository.save(a);
        }
    }

    @Transactional
    @Override
    public String achieve(String userId, AchieveReqDTO achieveReqDTO) {
        Achievement ach = achievementRepository.isSatisfyAchievementCondition(achieveReqDTO.getType(), achieveReqDTO.getCondition());
        if (ach == null) {
            return null;
        }

        if (achieveRepository.existsAchieveBetweenUserAndAchievement(userId, ach.getId()) != null) {
            return null;
        };

        LocalDateTime achievedAt = LocalDateTime.parse(LocalDateTime.now()
                .format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")));

        achieveRepository.createAchieveBetweenUserAndAchievement(userId,
                ach.getId(), achievedAt);

        String response = notificationServiceClient.sendNotificationByAction(userId,
                SendNotificationByActionReqDTO.builder().userId(userId).type(NotificationType.ACHIEVE).build()).getData();

        log.info(">>> 서버간 통신 후 response 수신: " + response);

        userRepository.increaseAchieveCount(userId);

        userRepository.createAvailableByAchieveCondition(userId);

        return userId;
    }

    @Override
    public GetAchievementListResDTO getAchievementList(String userId) {
        AchievementType[] arr = AchievementType.values();
        GetAchievementListResDTO getAchievementListResDTO = new GetAchievementListResDTO();

        for (int i = 0; i < arr.length; i++) {
            List<Achievement> achievementList = achievementRepository.getAchievementList(arr[i]);
            List<GetAchievementResDTO> getAchievementResList = new ArrayList<>();
            for (int j = 0; j < achievementList.size(); j++) {
                GetAchievementResDTO getAchievementResDTO = GetAchievementResDTO.builder()
                        .achieved(achieveRepository.existsAchieveBetweenUserAndAchievement(userId,
                                achievementList.get(j).getId()) != null)
                        .id(achievementList.get(j).getId())
                        .achName(achievementList.get(j).getAchName())
                        .build();
                getAchievementResList.add(getAchievementResDTO);
            }
            switch (i) {
                case 0 -> {
                    getAchievementListResDTO.setAttendance(getAchievementResList);
                }
                case 1 -> {
                    getAchievementListResDTO.setAchievement(getAchievementResList);
                }
                case 2 -> {
                    getAchievementListResDTO.setEvolution(getAchievementResList);
                }
                case 3 -> {
                    getAchievementListResDTO.setGraduation(getAchievementResList);
                }
                case 4 -> {
                    getAchievementListResDTO.setContinueAttendance(getAchievementResList);
                }
            }
        }
        return getAchievementListResDTO;
    }

    @Override
    @Transactional
    public GetAchievementDetailResDTO getAchievementDetail(String userId, String achievementId) {
        Achievement achievement = achievementRepository.getAchievementById(achievementId)
                .orElseThrow(() -> new CustomException(ErrorCode.ACHIEVEMENT_NOT_EXISTS));
        if (achieveRepository.existsAchieveBetweenUserAndAchievement(userId, achievementId) == null) {
            throw new CustomException(ErrorCode.DID_NOT_ACHIEVED_ACHIEVEMENT);
        };
        Achieve achieve = achieveRepository.findByUserIdAndAchievementId(userId, achievementId);
        return GetAchievementDetailResDTO.builder()
                .achName(achievement.getAchName())
                .achType(achievement.getAchType())
                .achDescribe(achievement.getAchDescribe())
                .achievedAt(achieve.getAchievedAt().toString())
                .achDiff(achievement.getAchDiff())
                .build();
    }

    @Override
    public CheckAchieveOrNotResDTO checkAchievementCondition(String userId, CheckAchieveOrNotReqDTO req) {
        Achievement achievement = achievementRepository.isSatisfyAchievementCondition(req.getType(), req.getCondition());
        if (achievement != null) {
            return CheckAchieveOrNotResDTO.builder().achieveOrNot(true).achievementId(achievement.getId()).build();
        };
        return CheckAchieveOrNotResDTO.builder().achieveOrNot(false).achievementId(null).build();
    }

    @Override
    @Transactional
    public void achieveTodoAchievement(String userId) {
        userRepository.increaseTodoClearCount(userId);
        LocalDateTime achievedAt = LocalDateTime.parse(LocalDateTime.now()
                .format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")));
        achieveRepository.achieveTodoAchievement(userId, AchievementType.ACHIEVE, achievedAt);
        userRepository.increaseAchieveCount(userId);
        String response = notificationServiceClient.sendNotificationByAction(userId, SendNotificationByActionReqDTO.builder()
                .userId(userId).type(NotificationType.ACHIEVE).build()).getData();
    }
}
