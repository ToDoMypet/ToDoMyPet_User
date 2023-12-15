package com.todomypet.userservice.dto;

import com.todomypet.userservice.domain.node.Pet;
import com.todomypet.userservice.domain.node.User;
import com.todomypet.userservice.domain.relationship.Adopt;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class GetAdoptedPetDTO {
    User user;
    Pet pet;
    Adopt adopt;
}
