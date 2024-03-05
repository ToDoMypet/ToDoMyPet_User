package com.todomypet.userservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
public class DuplicationCheckResDTO {
    private Boolean duplicationOrNot;
    private Boolean deletedOrNot;
}
