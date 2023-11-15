package com.todomypet.userservice.service;

import com.todomypet.userservice.dto.*;

public interface UserService {


    MyPageResDTO getMyPage(String userId);

    UserInfoResDTO getUserByPersonalCode(String personalCode);

    UserProfileResDTO getUserProfile(String userId, String targetId);
    MyProfileResDTO getMyProfile(String userId);
    void updateMyPage(String userId, UpdateMyPageReqDTO updateMyPageReqDTO);
}
