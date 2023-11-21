package com.todomypet.userservice.service;

import com.todomypet.userservice.dto.UserInfoResDTO;

import java.util.List;

public interface FriendService {
    void setFriendRelationship(String userId, String targetId);

    void deleteFriendRelationship(String userId, String targetId);

    List<UserInfoResDTO> getFriendsList(String userId);
    List<UserInfoResDTO> searchFriendsByNickname(String userId, String nickname);
 }
