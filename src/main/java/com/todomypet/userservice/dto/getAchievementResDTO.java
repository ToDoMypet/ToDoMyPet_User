package com.todomypet.userservice.dto;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class getAchievementResDTO {
    private String achName;
    private boolean achieved;
}
