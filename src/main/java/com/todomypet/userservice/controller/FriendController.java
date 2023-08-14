package com.todomypet.userservice.controller;

import com.todomypet.userservice.dto.SuccessResDTO;
import com.todomypet.userservice.service.FriendService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

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

    @DeleteMapping("/friend/{targetId}")
    SuccessResDTO<Void> deleteFriendRelationship(@RequestHeader String userId,
                                                 @PathVariable String targetId) {
        friendService.deleteFriendRelationship(userId, targetId);
        return new SuccessResDTO<Void>(null);
    }
}
