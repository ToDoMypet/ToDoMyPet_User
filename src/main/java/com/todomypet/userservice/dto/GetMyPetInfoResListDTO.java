package com.todomypet.userservice.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class GetMyPetInfoResListDTO {
    private List<GetMyPetInfoResDTO> petInfoList;
}
