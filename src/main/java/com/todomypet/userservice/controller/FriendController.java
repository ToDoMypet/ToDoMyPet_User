package com.todomypet.userservice.controller;

import com.todomypet.userservice.domain.relationship.Friend;
import com.todomypet.userservice.dto.FriendListResDTO;
import com.todomypet.userservice.dto.SuccessResDTO;
import com.todomypet.userservice.dto.UserInfoResDTO;
import com.todomypet.userservice.service.FriendService;
import com.todomypet.userservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class FriendController {

    private final FriendService friendService;

    @PostMapping("/friends/{targetId}")
    SuccessResDTO<Void> setFriendRelationship(@RequestHeader String userId,
                                          @PathVariable String targetId) {
        friendService.setFriendRelationship(userId, targetId);
        return new SuccessResDTO<Void>(null);
    }

    @DeleteMapping("/friends/{targetId}")
    SuccessResDTO<Void> deleteFriendRelationship(@RequestHeader String userId,
                                                 @PathVariable String targetId) {
        friendService.deleteFriendRelationship(userId, targetId);
        return new SuccessResDTO<Void>(null);
    }

    @GetMapping("/friends")
    SuccessResDTO<FriendListResDTO> getFriendsList(@RequestHeader String userId) {
        List<UserInfoResDTO> friends = friendService.getFriendsList(userId);
        FriendListResDTO response = FriendListResDTO.builder().friends(friends).build();
        return new SuccessResDTO<FriendListResDTO>(response);
    }
}
