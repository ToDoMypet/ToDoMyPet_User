package com.todomypet.userservice.dto.openFeign;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor
@Builder
@Getter
public class AddCategoryReqDTO {
    private String name;
    private String colorCode;
}
