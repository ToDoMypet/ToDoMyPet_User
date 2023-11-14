package com.todomypet.userservice.dto;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class MyPageResDTO {
    private String nickname;
    private String bio;
    private String profilePicUrl;
    private String personalCode;
    private Boolean protect;
}
