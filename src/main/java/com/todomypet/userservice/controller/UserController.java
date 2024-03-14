package com.todomypet.userservice.controller;

import com.todomypet.userservice.dto.*;
import com.todomypet.userservice.dto.openFeign.GetUserByTodoIdResDTO;
import com.todomypet.userservice.dto.pet.GetMainPageResDTO;
import com.todomypet.userservice.dto.user.AdminGetAllUsersDTO;
import com.todomypet.userservice.service.UserService;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@SecurityRequirement(name = "bearer-key")
@Tag(name = "User Controller")
public class UserController {

    private final UserService userService;

    @Operation(summary = "메인 페이지 조회", description = "메인 페이지 조회 API입니다.")
    @GetMapping("/main-page")
    public SuccessResDTO<GetMainPageResDTO> getMainPage(@RequestHeader String userId) {
        GetMainPageResDTO response = userService.getMainPage(userId);
        return new SuccessResDTO<GetMainPageResDTO>(response);
    }

    @Operation(summary = "회원 정보 조회", description = "회원 정보 페이지입니다.")
    @GetMapping("/profile/{targetId}")
    public SuccessResDTO<UserProfileResDTO> getUserProfile(@Parameter(hidden = true) @RequestHeader String userId,
                                                           @PathVariable String targetId) {
        UserProfileResDTO response = userService.getUserProfile(userId, targetId);
        return new SuccessResDTO<UserProfileResDTO>(response);
    }

    @Operation(summary = "내 정보 조회", description = "인증 사용자 정보 페이지입니다.")
    @GetMapping("/profile/my")
    public SuccessResDTO<MyProfileResDTO> getMyProfile(@Parameter(hidden = true) @RequestHeader String userId) {
        MyProfileResDTO response = userService.getMyProfile(userId);
        return new SuccessResDTO<MyProfileResDTO>(response);
    }

    @Operation(summary = "회원 설정 정보", description = "설정 탭에서 확인할 수 있는 회원 정보입니다." +
            " * 주의: 마이페이지 탭이 아닙니다. 마이페이지는 회원 정보 조회 API를 사용하세요.")
    @GetMapping("/setting/my-profile")
    public SuccessResDTO<MyPageResDTO> getMyPage(@Parameter(hidden = true) @RequestHeader String userId) {
        MyPageResDTO response = userService.getMyPage(userId);
        return new SuccessResDTO<MyPageResDTO>(response);
    }

    @Operation(summary = "회원 설정 정보 수정", description = "설정 탭에서 회원 정보를 수정합니다.")
    @PutMapping("/setting/my-profile")
    public SuccessResDTO<Void> updateMyPage(@Parameter(hidden = true) @RequestHeader String userId,
                                            @RequestBody UpdateMyPageReqDTO updateMyPageReqDTO) {
        userService.updateMyPage(userId, updateMyPageReqDTO);
        return new SuccessResDTO<Void>(null);
    }

    @Operation(summary = "개인 코드로 유저 검색",
            description = "개인 코드를 통해 유저를 검색합니다. 검색 결과가 없을 시 null이 return됩니다.")
    @GetMapping("/search/{personalCode}")
    public SuccessResDTO<UserInfoResDTO> getUserByPersonalCode(@Parameter(hidden = true) @RequestHeader String userId,
                                                               @PathVariable String personalCode) {
        UserInfoResDTO response = userService.getUserByPersonalCode(userId, personalCode);
        return new SuccessResDTO<UserInfoResDTO>(response);
    }
    @Hidden
    @PutMapping("/increase-collection-count")
    public SuccessResDTO<Integer> increaseCollectionCount(@RequestHeader String userId) {
        int collectionCount = userService.increaseCollectionCountByUserId(userId);
        return new SuccessResDTO<Integer>(collectionCount);
    }

    @Hidden
    @GetMapping("/increase-and-get-todo-clear-count")
    public SuccessResDTO<Integer> increaseTodoClearCount(@RequestHeader String userId) {
        int todoClearCount = userService.increaseTodoClearCountByUserId(userId);
        return new SuccessResDTO<Integer>(todoClearCount);
    }

    @Operation(description = "진화 카운트 조회")
    @GetMapping("/increase-and-get-pet-evolve-count")
    public SuccessResDTO<Integer> increasePetEvolveCount(@RequestHeader String userId) {
        int petEvolveCount = userService.increaseAndGetPetEvolveCountByUserId(userId);
        return new SuccessResDTO<Integer>(petEvolveCount);
    }

    @Operation(description = "졸업 카운트 조회")
    @GetMapping("/increase-and-get-pet-complete-count")
    public SuccessResDTO<Integer> increasePetCompleteCount(@RequestHeader String userId) {
        int petCompleteCount = userService.increaseAndGetPetCompleteCountByUserId(userId);
        return new SuccessResDTO<Integer>(petCompleteCount);
    }

    @GetMapping("/get-user-by-todo/{todoId}")
    public SuccessResDTO<GetUserByTodoIdResDTO> getUserIdByTodoId(@PathVariable String todoId) {
        String response = userService.getUserIdByTodoId(todoId);
        return new SuccessResDTO<GetUserByTodoIdResDTO>(GetUserByTodoIdResDTO.builder().userId(response).build());
    }
}
