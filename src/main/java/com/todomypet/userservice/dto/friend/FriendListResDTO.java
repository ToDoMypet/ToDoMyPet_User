package com.todomypet.userservice.dto.friend;

import com.todomypet.userservice.dto.UserInfoResDTO;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class FriendListResDTO {
    private List<UserInfoResDTO> friends;
}
