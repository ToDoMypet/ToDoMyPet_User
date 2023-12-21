package com.todomypet.userservice.dto;

import com.todomypet.userservice.domain.node.PetGradeType;
import com.todomypet.userservice.domain.node.PetPersonalityType;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class GetPetCollectionResDTO {
    private String id;
    private String petName;
    private String imageUrl;
    private boolean collected;
    private PetPersonalityType personality;
    private PetGradeType grade;
    private String describe;
}
