package com.todomypet.userservice.dto.pet;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class GetMainPetInfosResDTO {
    private String petGrade;
    private String petPortraitImageUrl;
    private String petGifUrl;
    private String petName;
    private int petExperiencePoint;
    private int petMaxExperiencePoint;
    private String petPersonalityType;
    private String petId;
    private String petSignatureCode;
    private String petSeq;
}
