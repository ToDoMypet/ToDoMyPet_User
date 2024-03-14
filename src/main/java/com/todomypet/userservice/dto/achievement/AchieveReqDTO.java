package com.todomypet.userservice.dto.achievement;

import com.todomypet.userservice.domain.node.AchievementType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AchieveReqDTO {
    private AchievementType type;
    private int condition;
}
