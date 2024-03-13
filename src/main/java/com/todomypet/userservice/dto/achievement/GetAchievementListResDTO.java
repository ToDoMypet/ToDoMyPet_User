package com.todomypet.userservice.dto.achievement;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class GetAchievementListResDTO {
    private List<GetAchievementResDTO> attendance;
    private List<GetAchievementResDTO> achievement;
    private List<GetAchievementResDTO> evolution;
    private List<GetAchievementResDTO> graduation;
    private List<GetAchievementResDTO> continueAttendance;
}
