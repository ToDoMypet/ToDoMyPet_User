package com.todomypet.userservice.dto.achievement;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AchieveReqDTO {
    private String achievementId;
}
