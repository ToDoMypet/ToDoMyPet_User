package com.todomypet.userservice.service;

import com.todomypet.userservice.dto.AchieveReqDTO;
import com.todomypet.userservice.dto.AchieveResDTO;
import com.todomypet.userservice.dto.AddAchievementReqDTO;

public interface AchievementService {
    String addAchievement(AddAchievementReqDTO addAchievementReqDTO);
    void achieve(String userId, AchieveReqDTO achieveReqDTO);
}
