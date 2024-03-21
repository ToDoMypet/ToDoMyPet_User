package com.todomypet.userservice.service;

import com.todomypet.userservice.domain.node.User;
import com.todomypet.userservice.dto.UserInfoResDTO;
import com.todomypet.userservice.dto.friend.SendFriendNotificationReqDTO;
import com.todomypet.userservice.dto.openFeign.NotificationType;
import com.todomypet.userservice.dto.openFeign.SendNotificationByActionReqDTO;
import com.todomypet.userservice.exception.CustomException;
import com.todomypet.userservice.exception.ErrorCode;
import com.todomypet.userservice.mapper.UserMapper;
import com.todomypet.userservice.repository.BlockRepository;
import com.todomypet.userservice.repository.FriendRepository;
import com.todomypet.userservice.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class FriendServiceImpl implements FriendService {

    private final FriendRepository friendRepository;
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final NotificationServiceClient notificationServiceClient;
    private final BlockRepository blockRepository;

    @Override
    @Transactional
    public void setFriendRelationship(String userId, String targetId) {
        log.info(">>> 친구 관계 생성: " + userId + ", " + targetId);
        User user = userRepository.getOneUserById(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_EXISTS));

        User target = userRepository.getOneUserById(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_EXISTS));

        friendRepository.setFriendRelationshipBetweenUserAndUser(userId, targetId);
        userRepository.increaseFriendCount(userId);
        userRepository.increaseFriendCount(targetId);

        try {
            notificationServiceClient.sendNotificationByAction(userId, SendNotificationByActionReqDTO.builder()
                    .userId(targetId).type(NotificationType.FRIEND).senderProfilePicUrl(user.getProfilePicUrl())
                    .senderName(user.getNickname()).notificationDataId(userId).build());
        } catch (Exception e) {
            log.error(">>> 알림 전송 실패: " + userId);
        }
    }

    @Override
    @Transactional
    public void deleteFriendRelationship(String userId, String targetId) {
        log.info(">>> 친구 관계 삭제: " + userId + ", " + targetId);
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

    @Override
    public List<UserInfoResDTO> searchFriendsByNickname(String userId, String nickname) {
        if (nickname == null) {
            throw new CustomException(ErrorCode.NICKNAME_IS_NULL);
        }
        User u = userRepository.getOneUserById(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_EXISTS));

        List<User> searchedFriends = userRepository.getFriendListByUserIdAndNickname(userId, nickname);
        List<UserInfoResDTO> response = new ArrayList<>();
        for (User user:searchedFriends) {
            response.add(userMapper.userToUserInfoResDTO(user));
        }
        return response;
    }
}
