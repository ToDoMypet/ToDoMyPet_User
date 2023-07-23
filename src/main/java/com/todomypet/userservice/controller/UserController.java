package com.todomypet.userservice.controller;

import com.todomypet.userservice.dto.MyPageResDTO;
import com.todomypet.userservice.dto.SuccessResDTO;
import com.todomypet.userservice.dto.UserInfoResDTO;
import com.todomypet.userservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/my-page")
    public SuccessResDTO<MyPageResDTO> getMyPage(@RequestHeader String userId) {
        MyPageResDTO response = userService.getMyPage(userId);
        return new SuccessResDTO<MyPageResDTO>(response);
    }
}
