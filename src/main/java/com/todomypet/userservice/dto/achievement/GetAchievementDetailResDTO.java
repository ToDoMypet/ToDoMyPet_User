package com.todomypet.userservice.dto.achievement;

import com.todomypet.userservice.domain.node.AchievementType;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class GetAchievementDetailResDTO {
    private String achName;
    private AchievementType achType;
    private String achievedAt;
    private int achDiff;
    private String achDescribe;
}
