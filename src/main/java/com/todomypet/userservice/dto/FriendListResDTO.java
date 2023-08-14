package com.todomypet.userservice.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class FriendListResDTO {
    private List<UserInfoResDTO> friends;
}
