package com.todomypet.userservice.dto.pet;

import com.todomypet.userservice.domain.node.PetGradeType;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class GetMyPetInfoResDTO {
    private String imageUrl;
    private String name;
    private int maxExperience;
    private int experience;
    private PetGradeType grade;
    private boolean graduated;
    private String seq;
}
