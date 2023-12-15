package com.todomypet.userservice.dto;

import com.todomypet.userservice.domain.node.PetGradeType;
import com.todomypet.userservice.domain.node.PetPersonalityType;
import com.todomypet.userservice.domain.node.PetType;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class PetDetailResDTO {
    private PetGradeType grade;
    private String name;
    private PetType type;
    private PetPersonalityType personality;
    private String description;
    private String portraitUrl;
}
