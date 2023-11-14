package com.todomypet.userservice.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GetAchievementResDTO {
    private String id;
    private String achName;
    private boolean achieved;
}
