package com.todomypet.userservice.service;

import com.todomypet.userservice.dto.achievement.AchieveReqDTO;
import com.todomypet.userservice.dto.achievement.AddAchievementReqDTO;
import com.todomypet.userservice.dto.achievement.GetAchievementDetailResDTO;
import com.todomypet.userservice.dto.achievement.GetAchievementListResDTO;

public interface AchievementService {
    String addAchievement(AddAchievementReqDTO addAchievementReqDTO);
    void achieve(String userId, AchieveReqDTO achieveReqDTO);
    GetAchievementListResDTO getAchievementList(String userId);
    GetAchievementDetailResDTO getAchievementDetail(String userId, String achievementId);
}
