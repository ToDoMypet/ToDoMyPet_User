package com.todomypet.userservice.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SignUpReqDTO {
    @NotBlank
    private String email;

    @Size(min = 8, max = 20)
    private String password;

    @Size(min = 1, max = 15)
    private String nickname;

    @Size(max = 80)
    private String bio;
}
