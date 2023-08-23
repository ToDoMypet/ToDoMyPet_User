package com.todomypet.userservice.service;

import com.todomypet.userservice.domain.node.User;
import com.todomypet.userservice.dto.UserInfoResDTO;
import com.todomypet.userservice.exception.CustomException;
import com.todomypet.userservice.exception.ErrorCode;
import com.todomypet.userservice.mapper.UserMapper;
import com.todomypet.userservice.repository.FriendRepository;
import com.todomypet.userservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FriendServiceImpl implements FriendService {

    private final FriendRepository friendRepository;
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public void setFriendRelationship(String userId, String targetId) {
        User user = userRepository.getOneUserById(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_EXISTS));

        User target = userRepository.getOneUserById(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_EXISTS));

        friendRepository.setFriendRelationshipBetweenUserAndUser(userId, targetId);
        userRepository.increaseFriendCount(userId);
        userRepository.increaseFriendCount(targetId);
    }

    @Override
    public void deleteFriendRelationship(String userId, String targetId) {
        User user = userRepository.getOneUserById(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_EXISTS));

        User target = userRepository.getOneUserById(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_EXISTS));

        friendRepository.deleteFriendRelationshipBetweenUserAndUser(userId, targetId);
        userRepository.decreaseFriendCount(userId);
        userRepository.decreaseFriendCount(targetId);
    }

    @Override
    public List<UserInfoResDTO> getFriendsList(String userId) {
        User u = userRepository.getOneUserById(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_EXISTS));

        List<User> friends = userRepository.getFriendListByUserId(userId);
        List<UserInfoResDTO> response = new ArrayList<>();
        for (User user:friends) {
            response.add(userMapper.userToUserInfoResDTO(user));
        }
        return response;
    }
}
