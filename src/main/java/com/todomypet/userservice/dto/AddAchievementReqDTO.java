package com.todomypet.userservice.dto;

import com.todomypet.userservice.domain.node.AchievementType;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class AddAchievementReqDTO {
    private String id;
    private String achName;
    private AchievementType achType;
    private int achDiff;
    private int achCondition;
    private String achAdvice;
    private String achDescribe;
}
