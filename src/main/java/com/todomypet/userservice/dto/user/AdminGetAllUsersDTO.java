package com.todomypet.userservice.dto.user;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class AdminGetAllUsersDTO {
   List<AdminUserDTO> userList;
}
