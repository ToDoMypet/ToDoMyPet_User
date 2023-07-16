package com.todomypet.userservice.service;

import com.todomypet.userservice.dto.UserInfoResDTO;

public interface UserService {
    UserInfoResDTO getOneUserInfo(String userId);
}
