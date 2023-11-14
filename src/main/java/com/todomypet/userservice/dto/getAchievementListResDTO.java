package com.todomypet.userservice.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class getAchievementListResDTO {
    private List<getAchievementResDTO> attendance;
    private List<getAchievementResDTO> achievement;
    private List<getAchievementResDTO> evolution;
    private List<getAchievementResDTO> graduation;
    private List<getAchievementResDTO> acquirement;
}
