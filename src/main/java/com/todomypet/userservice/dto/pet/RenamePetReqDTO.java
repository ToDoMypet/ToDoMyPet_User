package com.todomypet.userservice.dto.pet;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class RenamePetReqDTO {
    private String signatureCode;
    private String rename;
}
