package com.todomypet.userservice.controller;

import com.todomypet.userservice.dto.SuccessResDTO;
import com.todomypet.userservice.service.FriendService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class FriendController {

    private final FriendService friendService;
    @PostMapping("/friend/{targetId}")
    SuccessResDTO<Void> setFriendRelationship(@RequestHeader String userId,
                                          @PathVariable String targetId) {
        friendService.setFriendRelationship(userId, targetId);
        return new SuccessResDTO<Void>(null);
    }
}
