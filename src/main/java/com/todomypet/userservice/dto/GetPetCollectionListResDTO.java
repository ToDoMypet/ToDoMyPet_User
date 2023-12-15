package com.todomypet.userservice.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class GetPetCollectionListResDTO {
    private List<GetPetCollectionResDTO> bread;
    private List<GetPetCollectionResDTO> ghost;
}
