package com.todomypet.userservice.dto.pet;

import com.todomypet.userservice.domain.node.PetGradeType;
import com.todomypet.userservice.domain.node.PetPersonalityType;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class GetMainPageResDTO {
    private PetGradeType petGrade;
    private String petPortraitImage;
    private String petGif;
    private String petName;
    private PetPersonalityType petPersonality;
    private String petSignatureCode;
    private String petSeq;
    private int petExperiencePoint;
    private int petMaxExperiencePoint;
    private String backgroundId;
    private String backgroundImage;
}
