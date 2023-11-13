package com.todomypet.userservice.domain.node;

import lombok.Getter;

@Getter
public enum AchievementType {
    ATTENDANCE("출석"),
    ACHIEVE("달성"),
    EVOLUTION("진화"),
    GRADUATION("최종"),
    ACQUIREMENT("획득")
    ;

    private final String achievementType;

    AchievementType(String achievementType) {
        this.achievementType = achievementType;
    }
}
