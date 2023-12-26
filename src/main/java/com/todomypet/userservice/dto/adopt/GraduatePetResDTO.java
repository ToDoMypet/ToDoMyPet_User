package com.todomypet.userservice.dto.adopt;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class GraduatePetResDTO {
    private String petImageUrl;
    private String petName;
}
