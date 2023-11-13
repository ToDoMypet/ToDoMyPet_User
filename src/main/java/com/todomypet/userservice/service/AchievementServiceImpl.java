package com.todomypet.userservice.service;

import com.todomypet.userservice.domain.node.Achievement;
import com.todomypet.userservice.dto.AchieveReqDTO;
import com.todomypet.userservice.dto.AchieveResDTO;
import com.todomypet.userservice.dto.AddAchievementReqDTO;
import com.todomypet.userservice.exception.CustomException;
import com.todomypet.userservice.exception.ErrorCode;
import com.todomypet.userservice.repository.AchievementRepository;
import com.todomypet.userservice.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
@RequiredArgsConstructor
public class AchievementServiceImpl implements AchievementService {

    private final AchievementRepository achievementRepository;
    private final UserRepository userRepository;

    @Transactional
    @Override
    public String addAchievement(AddAchievementReqDTO addAchievementReqDTO) {
        Achievement a = Achievement.builder().id(addAchievementReqDTO.getId())
                .achName(addAchievementReqDTO.getAchName())
                .achType(addAchievementReqDTO.getAchType())
                .achDiff(addAchievementReqDTO.getAchDiff())
                .achCondition(addAchievementReqDTO.getAchCondition())
                .achAdvice(addAchievementReqDTO.getAchAdvice())
                .achDescribe(addAchievementReqDTO.getAchDescribe())
                .build();
        return achievementRepository.save(a).getId();
    }

    @Transactional
    @Override
    public void achieve(String userId, AchieveReqDTO achieveReqDTO) {
        LocalDateTime achievedAt = LocalDateTime.parse(LocalDateTime.now()
                .format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")));

        if (achievementRepository.existsAchieveBetweenUserAndAchievement(userId, achieveReqDTO.getAchievementId())) {
            throw new CustomException(ErrorCode.ALREADY_ACHIEVE);
        };

        userRepository.increaseAchieveCount(userId);

        achievementRepository.createAchieveBetweenUserAndAchievement(userId,
                achieveReqDTO.getAchievementId(), achievedAt.toString());
    }
}
