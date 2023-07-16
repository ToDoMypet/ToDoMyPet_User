package com.todomypet.userservice.controller;

import com.todomypet.userservice.dto.SuccessResDTO;
import com.todomypet.userservice.dto.UserInfoResDTO;
import com.todomypet.userservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/users/my")
    public SuccessResDTO<UserInfoResDTO> getOneUserInfo(@RequestHeader String userId) {
        UserInfoResDTO response = userService.getOneUserInfo(userId);
        return new SuccessResDTO<UserInfoResDTO>(response);
    }
}
