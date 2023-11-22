package com.todomypet.userservice.service;

import com.todomypet.userservice.domain.node.User;
import com.todomypet.userservice.dto.*;
import com.todomypet.userservice.exception.CustomException;
import com.todomypet.userservice.exception.ErrorCode;
import com.todomypet.userservice.mapper.UserMapper;
import com.todomypet.userservice.repository.FriendRepository;
import com.todomypet.userservice.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
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
    @Transactional
    public void updateMyPage(String userId, UpdateMyPageReqDTO updateMyPageReqDTO) {
        log.info(">>> 회원 정보 수정 : " + userId);

        if (updateMyPageReqDTO.getProfilePicUrl().equals("clear")) {
            userRepository.updateMyProfileImage(userId, "");
        }

        if (updateMyPageReqDTO.getProfilePicUrl() != null) {
            String imageUrl = s3Uploader.upload(updateMyPageReqDTO.getProfilePicUrl());
            userRepository.updateMyProfileImage(userId, imageUrl);
        }

        userRepository.updateMyPage(userId, updateMyPageReqDTO.getNickname(), updateMyPageReqDTO.getBio(),
                updateMyPageReqDTO.getProtect());
    }
}
