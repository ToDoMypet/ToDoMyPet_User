package com.todomypet.userservice.service;

import com.todomypet.userservice.domain.node.Achievement;
import com.todomypet.userservice.domain.node.AchievementType;
import com.todomypet.userservice.dto.*;
import com.todomypet.userservice.exception.CustomException;
import com.todomypet.userservice.exception.ErrorCode;
import com.todomypet.userservice.repository.AchieveRepository;
import com.todomypet.userservice.repository.AchievementRepository;
import com.todomypet.userservice.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class AchievementServiceImpl implements AchievementService {

    private final AchievementRepository achievementRepository;
    private final AchieveRepository achieveRepository;
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

        if (achieveRepository.existsAchieveBetweenUserAndAchievement(userId, achieveReqDTO.getAchievementId())) {
            throw new CustomException(ErrorCode.ALREADY_ACHIEVE);
        };

        userRepository.increaseAchieveCount(userId);

        achieveRepository.createAchieveBetweenUserAndAchievement(userId,
                achieveReqDTO.getAchievementId(), achievedAt.toString());
    }

    @Override
    public GetAchievementListResDTO getAchievementList(String userId) {
        AchievementType[] arr = {AchievementType.ATTENDANCE, AchievementType.ACHIEVE, AchievementType.EVOLUTION,
                AchievementType.GRADUATION, AchievementType.ACQUIREMENT};

        for (int i = 0; i < arr.length; i++) {
            GetAchievementResDTO getAchievementResDTO = achievementRepository.getAchievementList(userId, arr[i]);
            System.out.println(getAchievementResDTO.getId());
            System.out.println(getAchievementResDTO.getAchName());
            System.out.println(getAchievementResDTO.isAchieved());
        }
        return null;
    }
}
