package com.todomypet.userservice.dto.achievement;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class CheckAchieveOrNotResDTO {
    private boolean achieveOrNot;
    private String achievementId;
}
