package com.todomypet.userservice.dto.pet;

import com.todomypet.userservice.domain.node.PetGradeType;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class GetAvailableStartingPetDTO {
    private String id;
    private PetGradeType petGrade;
    private String petName;
    private String petImageUrl;
}
