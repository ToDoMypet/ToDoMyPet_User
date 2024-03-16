package com.todomypet.userservice.service;

import com.todomypet.userservice.dto.achievement.*;

public interface AchievementService {
    void addAchievement(AddAchievementReqDTOList addAchievementReqDTOList);
    String achieve(String userId, AchieveReqDTO achieveReqDTO);
    GetAchievementListResDTO getAchievementList(String userId);
    GetAchievementDetailResDTO getAchievementDetail(String userId, String achievementId);

    CheckAchieveOrNotResDTO checkAchievementCondition(String userId, CheckAchieveOrNotReqDTO req);

    boolean achieveTodoAchievement(String userId);
}
