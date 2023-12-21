package com.todomypet.userservice.dto.pet;

import com.todomypet.userservice.domain.node.PetGradeType;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class GetPetUpgradeChoiceResDTO {
    private String petId;
    private String petName;
    private PetGradeType petGrade;
    private String petImageUrl;
    private boolean getOrNot;
}
