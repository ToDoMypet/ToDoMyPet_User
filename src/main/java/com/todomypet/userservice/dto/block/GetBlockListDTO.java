package com.todomypet.userservice.dto.block;

import com.todomypet.userservice.dto.UserInfoResDTO;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class GetBlockListDTO {
    private List<UserInfoResDTO> userList;
}
