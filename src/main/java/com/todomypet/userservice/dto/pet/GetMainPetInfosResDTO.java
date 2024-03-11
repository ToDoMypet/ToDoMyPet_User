package com.todomypet.userservice.dto.pet;

import com.todomypet.userservice.domain.node.PetPersonalityType;
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
    private PetPersonalityType petPersonalityType;
    private String petId;
    private String petSignatureCode;
    private String petSeq;
}
