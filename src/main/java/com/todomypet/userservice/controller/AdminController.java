package com.todomypet.userservice.controller;

import com.todomypet.userservice.dto.SuccessResDTO;
import com.todomypet.userservice.dto.user.AdminGetAllUsersDTO;
import com.todomypet.userservice.service.UserService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@SecurityRequirement(name = "bearer-key")
@Tag(name = "User Admin Controller")
@RequestMapping("/admin")
public class AdminController {

    private final UserService userService;

    @GetMapping("/get-all-users")
    public SuccessResDTO<AdminGetAllUsersDTO> adminGetAllUsers() {
        AdminGetAllUsersDTO response = userService.getAllUsers();
        return new SuccessResDTO<AdminGetAllUsersDTO>(response);
    }

}
