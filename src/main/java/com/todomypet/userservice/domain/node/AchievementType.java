package com.todomypet.userservice.domain.node;

import lombok.Getter;

@Getter
public enum AchievementType {
    ATTENDANCE("출석")
    ;

    private final String achievementType;

    AchievementType(String achievementType) {
        this.achievementType = achievementType;
    }
}
