package com.todomypet.userservice.service;

import com.todomypet.userservice.dto.MyPageResDTO;
import com.todomypet.userservice.dto.UserInfoResDTO;

public interface UserService {

    MyPageResDTO getMyPage(String userId);

    UserInfoResDTO getUserByPersonalCode(String personalCode);
}
