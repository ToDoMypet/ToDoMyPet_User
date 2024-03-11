package com.todomypet.userservice.dto.openFeign;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class GetUserByTodoIdResDTO {
    private String userId;
}
