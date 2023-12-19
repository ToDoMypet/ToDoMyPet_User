package com.todomypet.userservice.dto.pet;

import lombok.Builder;

@Builder
public class GraduatedPetResDTO {
    private boolean renameOrNot;
    private String originName;
    private String rename;
    private String petImageUrl;
}
