package com.todomypet.userservice.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class UserInfoResDTO {
    private String id;
    private String nickname;
    private String bio;
    private String profilePicUrl;
    private boolean friendOrNot;
}
