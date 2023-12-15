package com.todomypet.userservice.dto;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class RenamePetReqDTO {
    private String signatureCode;
    private String rename;
}
