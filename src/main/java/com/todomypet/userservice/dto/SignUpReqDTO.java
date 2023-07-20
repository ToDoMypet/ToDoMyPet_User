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

    @Min(value = 8) @Max(value = 20)
    private String password;

    @Min(value = 1) @Max(value = 15)
    private String nickname;

    @Max(value = 80)
    private String bio;
}
