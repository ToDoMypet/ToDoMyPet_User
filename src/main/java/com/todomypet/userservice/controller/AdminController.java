package com.todomypet.userservice.controller;

import com.todomypet.userservice.dto.SignUpReqDTO;
import com.todomypet.userservice.dto.SuccessResDTO;
import com.todomypet.userservice.dto.user.AdminGetAllUsersDTO;
import com.todomypet.userservice.service.SignService;
import com.todomypet.userservice.service.UserService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@SecurityRequirement(name = "bearer-key")
@Tag(name = "User Admin Controller")
@RequestMapping("/admin")
public class AdminController {

    private final UserService userService;
    private final SignService signService;

    @PostMapping("/add-admin-account")
    public SuccessResDTO<Void> addAdminAccount(@RequestBody SignUpReqDTO signUpInfo) {
        signService.addAdminInfo(signUpInfo);
        return new SuccessResDTO<Void>(null);
    }

    @GetMapping("/get-all-users")
    public SuccessResDTO<AdminGetAllUsersDTO> adminGetAllUsers() {
        AdminGetAllUsersDTO response = userService.getAllUsers();
        return new SuccessResDTO<AdminGetAllUsersDTO>(response);
    }

}
