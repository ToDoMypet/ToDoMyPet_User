package com.todomypet.userservice.dto;

import com.todomypet.userservice.domain.node.PetGradeType;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class GetMyPetInfoResDTO {
    private String portraitUrl;
    private String name;
    private int maxExperience;
    private int experience;
    private PetGradeType grade;
    private boolean graduated;
}
