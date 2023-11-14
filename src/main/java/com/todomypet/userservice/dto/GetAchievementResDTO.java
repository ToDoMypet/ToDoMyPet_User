package com.todomypet.userservice.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Builder
public class GetAchievementResDTO {
    private String id;
    private String achName;
    private boolean achieved;
}
