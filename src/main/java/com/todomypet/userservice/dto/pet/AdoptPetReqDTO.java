package com.todomypet.userservice.dto.pet;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class AdoptPetReqDTO {
    private String petId;
    private String rename;
}
