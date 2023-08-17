package com.todomypet.userservice.controller;

import com.todomypet.userservice.dto.MyPageResDTO;
import com.todomypet.userservice.dto.SuccessResDTO;
import com.todomypet.userservice.dto.UserInfoResDTO;
import com.todomypet.userservice.service.UserService;
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

    @Operation(summary = "마이페이지", description = "설정 탭에서 확인할 수 있는 회원 정보입니다.")
    @GetMapping("/my-page")
    public SuccessResDTO<MyPageResDTO> getMyPage(@Parameter(hidden = true) @RequestHeader String userId) {
        MyPageResDTO response = userService.getMyPage(userId);
        return new SuccessResDTO<MyPageResDTO>(response);
    }

    @Operation(summary = "개인 코드로 유저 검색",
            description = "개인 코드를 통해 유저를 검색합니다. 검색 결과가 없을 시 null이 return됩니다.")
    @GetMapping("/search/{personalCode}")
    public SuccessResDTO<UserInfoResDTO> getUserByPersonalCode(@Parameter(hidden = true) @RequestHeader String userId,
                                                               @PathVariable String personalCode) {
        UserInfoResDTO response = userService.getUserByPersonalCode(personalCode);
        return new SuccessResDTO<UserInfoResDTO>(response);
    }
}
