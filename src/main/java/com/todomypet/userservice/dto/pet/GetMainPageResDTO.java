package com.todomypet.userservice.dto.pet;

import com.todomypet.userservice.domain.node.PetGradeType;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class GetMainPageResDTO {
    private PetGradeType petGrade;
    private String petPortraitImage;
    private String petGif;
    private String petName;
    private int petExperiencePoint;
    private int petMaxExperiencePoint;
    private String backgroundImage;
}
