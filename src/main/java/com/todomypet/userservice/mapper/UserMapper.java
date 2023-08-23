package com.todomypet.userservice.mapper;

import com.todomypet.userservice.domain.node.User;
import com.todomypet.userservice.dto.GetUserDetailsDTO;
import com.todomypet.userservice.dto.MyPageResDTO;
import com.todomypet.userservice.dto.UserInfoResDTO;
import com.todomypet.userservice.dto.UserProfileResDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {
    @Mapping(target = "Protected", source = "protected")
    MyPageResDTO userToMyPageResDTO(User user);

    GetUserDetailsDTO userToGetUserDetailsDTO(User user);

    UserInfoResDTO userToUserInfoResDTO(User user);
    UserProfileResDTO userToUserProfileResDTO(User user);
}
