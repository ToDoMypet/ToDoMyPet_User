package com.todomypet.userservice.mapper;

import com.todomypet.userservice.domain.node.User;
import com.todomypet.userservice.dto.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {
    @Mapping(target = "protect", source = "protected")
    MyPageResDTO userToMyPageResDTO(User user);

    GetUserDetailsDTO userToGetUserDetailsDTO(User user);

    UserInfoResDTO userToUserInfoResDTO(User user);
    @Mapping(target = "protect", source = "protected")
    UserProfileResDTO userToUserProfileResDTO(User user);
    MyProfileResDTO userToMyProfileResDTO(User user);
}
