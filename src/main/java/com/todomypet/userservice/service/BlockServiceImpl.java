package com.todomypet.userservice.service;

import com.todomypet.userservice.domain.node.User;
import com.todomypet.userservice.dto.UserInfoResDTO;
import com.todomypet.userservice.dto.block.GetBlockListDTO;
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
@Slf4j
@RequiredArgsConstructor
public class BlockServiceImpl implements BlockService {

    private final FriendRepository friendRepository;
    private final UserRepository userRepository;
    private final BlockRepository blockRepository;

    @Override
    @Transactional
    public void setBlockRelationship(String userId, String targetId) {

        if (friendRepository.existsFriendByUserAndUser(userId, targetId)) {
            friendRepository.deleteFriendRelationshipBetweenUserAndUser(userId, targetId);
            userRepository.decreaseFriendCount(userId);
            userRepository.decreaseFriendCount(targetId);
        }

        blockRepository.setBlockRelationshipBetweenUserAndUser(userId, targetId);
    }

    @Override
    public void deleteBlockRelationship(String userId, String targetId) {
        log.info(">>> 유저 차단 해제: from " + userId + " to " + targetId);

        blockRepository.deleteBlockRelationshipBetweenUserAndUser(userId, targetId);
    }

    @Override
    public GetBlockListDTO getBlockListByUserId(String userId) {
        List<User> userList = userRepository.getBlockListByUserId(userId);
        List<UserInfoResDTO> response = new ArrayList<>();

        for (User user : userList) {
            response.add(UserInfoResDTO.builder().id(user.getId())
                    .nickname(user.getNickname())
                    .profilePicUrl(user.getProfilePicUrl())
                    .build());
        }
        return GetBlockListDTO.builder().userList(response).build();
    }
}
