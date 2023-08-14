package com.todomypet.userservice.service;

public interface FriendService {
    void setFriendRelationship(String userId, String targetId);

    void deleteFriendRelationship(String userId, String targetId);
}
