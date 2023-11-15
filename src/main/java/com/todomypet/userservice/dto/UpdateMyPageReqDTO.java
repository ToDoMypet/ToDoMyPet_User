package com.todomypet.userservice.dto;

import lombok.*;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateMyPageReqDTO {
    private String nickname;
    private String bio;
    private String profilePicUrl;
    private Boolean protect;
}
