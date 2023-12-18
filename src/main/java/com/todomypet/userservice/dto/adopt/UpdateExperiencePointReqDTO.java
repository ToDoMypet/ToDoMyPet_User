package com.todomypet.userservice.dto.adopt;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateExperiencePointReqDTO {
    private String petSeqId;
    private int experiencePoint;
}
