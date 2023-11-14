package com.todomypet.userservice.dto;

import com.todomypet.userservice.domain.node.AchievementType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AddAchievementReqDTO {
    private String id;
    private String achName;
    private AchievementType achType;
    private int achDiff;
    private int achCondition;
    private String achAdvice;
    private String achDescribe;
}
