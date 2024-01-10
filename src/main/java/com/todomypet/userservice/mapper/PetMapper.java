package com.todomypet.userservice.mapper;

import com.todomypet.userservice.domain.node.Pet;
import com.todomypet.userservice.dto.pet.GetAvailableStartingPetDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PetMapper {
    GetAvailableStartingPetDTO petToGetAvailableStartingPetDTO(Pet pet);
}
