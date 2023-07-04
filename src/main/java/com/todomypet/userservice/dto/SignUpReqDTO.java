package com.todomypet.userservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SignUpReqDTO {
    private String email;
    private String password;
    private String nickname;
    private String bio;
}
