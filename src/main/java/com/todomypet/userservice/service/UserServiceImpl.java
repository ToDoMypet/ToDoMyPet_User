package com.todomypet.userservice.service;

import com.todomypet.userservice.domain.node.User;
import com.todomypet.userservice.dto.*;
import com.todomypet.userservice.exception.CustomException;
import com.todomypet.userservice.exception.ErrorCode;
import com.todomypet.userservice.mapper.UserMapper;
import com.todomypet.userservice.repository.FriendRepository;
import com.todomypet.userservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final FriendRepository friendRepository;
    private final S3Uploader s3Uploader;

    @Override
    public MyPageResDTO getMyPage(String userId) {
        User user = userRepository.getOneUserById(userId).orElseThrow(()
                -> new CustomException(ErrorCode.USER_NOT_EXISTS));

        MyPageResDTO myPageResDTO = userMapper.userToMyPageResDTO(user);
        return myPageResDTO;
    }

    @Override
    public UserInfoResDTO getUserByPersonalCode(String personalCode) {
        User user = userRepository.getOneUserByPersonalCode(personalCode).orElse(null);

        if (user == null) {
            return null;
        } else {
            return userMapper.userToUserInfoResDTO(user);
        }
    }

    @Override
    public UserProfileResDTO getUserProfile(String userId, String targetId) {
        User user = userRepository.getOneUserById(targetId).orElseThrow(()
                -> new CustomException(ErrorCode.USER_NOT_EXISTS));
        UserProfileResDTO userProfileResDTO = userMapper.userToUserProfileResDTO(user);
        userProfileResDTO.setFriendRelationship(friendRepository.existsFriendByUserAndUser(userId, targetId));
        return userProfileResDTO;
    }

    @Override
    public MyProfileResDTO getMyProfile(String userId) {
        User user = userRepository.getOneUserById(userId).orElseThrow(()
                -> new CustomException(ErrorCode.USER_NOT_EXISTS));
        return userMapper.userToMyProfileResDTO(user);
    }

    @Override
    public void updateMyPage(String userId, UpdateMyPageReqDTO updateMyPageReqDTO) {

        String imageUrl = "";
        if (updateMyPageReqDTO.getProfilePicUrl() != null) {
            imageUrl = s3Uploader.upload(updateMyPageReqDTO.getProfilePicUrl());
        }

        userRepository.updateMyPage(userId, updateMyPageReqDTO.getNickname(), updateMyPageReqDTO.getBio(),
                imageUrl, updateMyPageReqDTO.getProtect());
    }
}
