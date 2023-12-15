package com.todomypet.userservice.dto.pet;

import com.todomypet.userservice.dto.pet.AdoptedPetResDTO;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class AdoptedPetResListDTO {
    List<AdoptedPetResDTO> petList;
}
