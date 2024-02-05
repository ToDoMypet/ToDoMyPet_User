package com.todomypet.userservice.service;

import com.todomypet.userservice.dto.*;
import com.todomypet.userservice.dto.pet.GetMainPageResDTO;
import com.todomypet.userservice.dto.user.AdminGetAllUsersDTO;

public interface UserService {


    MyPageResDTO getMyPage(String userId);

    UserInfoResDTO getUserByPersonalCode(String userId, String personalCode);

    UserProfileResDTO getUserProfile(String userId, String targetId);
    MyProfileResDTO getMyProfile(String userId);
    void updateMyPage(String userId, UpdateMyPageReqDTO updateMyPageReqDTO);

    GetMainPageResDTO getMainPage(String userId);

    void increaseCollectionCountByUserId(String userId);

    void increasePetAcquireCountByUserId(String userId);

    void increasePetEvolveCountByUserId(String userId);

    void increasePetCompleteCountByUserId(String userId);

    AdminGetAllUsersDTO getAllUsers();
}
