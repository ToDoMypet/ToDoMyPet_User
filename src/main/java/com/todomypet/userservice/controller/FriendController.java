package com.todomypet.userservice.controller;

import com.todomypet.userservice.dto.FriendListResDTO;
import com.todomypet.userservice.dto.SuccessResDTO;
import com.todomypet.userservice.dto.UserInfoResDTO;
import com.todomypet.userservice.service.FriendService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@SecurityRequirement(name = "bearer-key")
@Tag(name = "Friend Controller")
public class FriendController {

    private final FriendService friendService;

    @Operation(summary = "친구 추가", description = "로그인된 사용자와 target 사용자 사이에 친구 관계를 생성합니다.")
    @PostMapping("/friends/{targetId}")
    SuccessResDTO<Void> setFriendRelationship(@RequestHeader String userId,
                                          @PathVariable String targetId) {
        friendService.setFriendRelationship(userId, targetId);
        return new SuccessResDTO<Void>(null);
    }

    @Operation(summary = "친구 삭제", description = "로그인된 사용자와 target 사용자 사이에 친구 관계를 삭제합니다.")
    @DeleteMapping("/friends/{targetId}")
    SuccessResDTO<Void> deleteFriendRelationship(@RequestHeader String userId,
                                                 @PathVariable String targetId) {
        friendService.deleteFriendRelationship(userId, targetId);
        return new SuccessResDTO<Void>(null);
    }

    @Operation(summary = "친구 목록 조회", description = "로그인된 사용자의 친구 목록을 조회합니다.")
    @GetMapping("/friends")
    SuccessResDTO<FriendListResDTO> getFriendsList(@RequestHeader String userId) {
        List<UserInfoResDTO> friends = friendService.getFriendsList(userId);
        FriendListResDTO response = FriendListResDTO.builder().friends(friends).build();
        return new SuccessResDTO<FriendListResDTO>(response);
    }
}
