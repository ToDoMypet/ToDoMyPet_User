package com.todomypet.userservice.service;

import com.todomypet.userservice.domain.node.Achievement;
import com.todomypet.userservice.dto.AddAchievementReqDTO;
import com.todomypet.userservice.repository.AchievementRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AchievementServiceImpl implements AchievementService {

    private final AchievementRepository achievementRepository;

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
}
