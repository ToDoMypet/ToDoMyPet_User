package com.todomypet.userservice.dto;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class UserInfoResDTO {
    private String nickname;
    private String bio;
    private String profilePicUrl;
}
