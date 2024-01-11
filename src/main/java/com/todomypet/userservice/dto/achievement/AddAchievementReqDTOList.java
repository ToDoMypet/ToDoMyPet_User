package com.todomypet.userservice.dto.achievement;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AddAchievementReqDTOList {
    private List<AddAchievementReqDTO> achievementList;
}
