package com.todomypet.userservice.dto;

import lombok.Builder;

@Builder
public class UserInfoResDTO {
    private String nickname;
    private String bio;
    private String profilePicUrl;
}
