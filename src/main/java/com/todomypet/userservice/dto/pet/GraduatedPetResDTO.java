package com.todomypet.userservice.dto.pet;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class GraduatedPetResDTO {
    private boolean renameOrNot;
    private String originName;
    private String currentName;
    private String petImageUrl;
}
