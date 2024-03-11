package com.todomypet.userservice.dto.adopt;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UpdateExperiencePointResDTO {
    private int updatedExperiencePoint;
}
