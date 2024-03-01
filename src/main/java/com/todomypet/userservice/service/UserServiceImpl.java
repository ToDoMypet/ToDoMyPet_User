package com.todomypet.userservice.service;

import com.todomypet.userservice.domain.node.*;
import com.todomypet.userservice.domain.relationship.Adopt;
import com.todomypet.userservice.dto.*;
import com.todomypet.userservice.dto.pet.GetMainPageResDTO;
import com.todomypet.userservice.dto.pet.GetMainPetInfosResDTO;
import com.todomypet.userservice.dto.user.AdminGetAllUsersDTO;
import com.todomypet.userservice.dto.user.AdminUserDTO;
import com.todomypet.userservice.exception.CustomException;
import com.todomypet.userservice.exception.ErrorCode;
import com.todomypet.userservice.mapper.UserMapper;
import com.todomypet.userservice.repository.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final FriendRepository friendRepository;
    private final S3Uploader s3Uploader;
    private final PetServiceClient petServiceClient;
    private final BackgroundRepository backgroundRepository;
    private final PetRepository petRepository;

    @Override
    public MyPageResDTO getMyPage(String userId) {
        User user = userRepository.getOneUserById(userId).orElseThrow(()
                -> new CustomException(ErrorCode.USER_NOT_EXISTS));

        MyPageResDTO myPageResDTO = userMapper.userToMyPageResDTO(user);
        return myPageResDTO;
    }

    @Override
    public UserInfoResDTO getUserByPersonalCode(String userId, String personalCode) {
        User user = userRepository.getOneUserByPersonalCode(personalCode).orElse(null);
        if (user == null) {
            return null;
        } else {
            UserInfoResDTO response = userMapper.userToUserInfoResDTO(user);
            response.setFriendOrNot(friendRepository.existsFriendByUserAndUser(userId, user.getId()));
            return response;
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
            userRepository.updateMyProfileImage(userId, null);
        } else if (!updateMyPageReqDTO.getProfilePicUrl().equals("unwavering")){
            String imageUrl = s3Uploader.upload(updateMyPageReqDTO.getProfilePicUrl());
            userRepository.updateMyProfileImage(userId, imageUrl);
        }

        userRepository.updateMyPage(userId, updateMyPageReqDTO.getNickname(), updateMyPageReqDTO.getBio(),
                updateMyPageReqDTO.getProtect());
    }

    @Override
    @Transactional
    public GetMainPageResDTO getMainPage(String userId) {
        log.info(">>> 메인 페이지 진입: " + userId);
        User user = userRepository.getOneUserById(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_EXISTS));
        GetMainPetInfosResDTO petInfos;

        try {
            petInfos = petServiceClient.getMainPetInfosByUserId(userId).getData();
        } catch (Exception e) {
            throw new CustomException(ErrorCode.FEIGN_CLIENT_ERROR);
        }


        Background b = backgroundRepository.getMainBackgroundByUserId(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_EXISTS_BACKGROUND));
        return GetMainPageResDTO.builder().petGrade(PetGradeType.valueOf(petInfos.getPetGrade()))
                .petPortraitImage(petInfos.getPetPortraitImageUrl())
                .petGif(petInfos.getPetGifUrl()).petName(petInfos.getPetName())
                .petExperiencePoint(petInfos.getPetExperiencePoint())
                .petPersonality(PetPersonalityType.valueOf(petInfos.getPetPersonalityType()))
                .petMaxExperiencePoint(petInfos.getPetMaxExperiencePoint())
                .petSignatureCode(petInfos.getPetSignatureCode())
                .petId(petInfos.getPetId())
                .petSeq(petInfos.getPetSeq())
                .backgroundId(b.getId())
                .backgroundImage(b.getBackgroundImageUrl())
                .build();
    }

    @Override
    public void increaseCollectionCountByUserId(String userId) {
        userRepository.increaseCollectionCount(userId);
    }

    @Override
    @Transactional
    public int increaseTodoClearCountByUserId(String userId) {
        userRepository.increaseTodoClearCount(userId);
        return userRepository.getTodoClearCountByUserId(userId);
    }

    @Override
    public void increasePetEvolveCountByUserId(String userId) {
        userRepository.increasePetEvolveCount(userId);
    }

    @Override
    public void increasePetCompleteCountByUserId(String userId) {
        userRepository.increasePetCompleteCount(userId);
    }

    @Override
    public AdminGetAllUsersDTO getAllUsers() {
        List<User> users = userRepository.getAllUsers();
        List<AdminUserDTO> response = new ArrayList<>();
        for (User user : users) {
            response.add(userMapper.userToAdminUserDTO(user));
        }
        return AdminGetAllUsersDTO.builder().userList(response).build();
    }

    @Override
    @Transactional
    public int increaseAchieveCount(String userId) {
        userRepository.increaseAchieveCount(userId);
        return userRepository.getAchieveCountByUserId(userId);
    }
}
