package com.todomypet.userservice.controller;

import com.todomypet.userservice.dto.SignUpReqDTO;
import com.todomypet.userservice.dto.SuccessResDTO;
import com.todomypet.userservice.dto.achievement.AddAchievementReqDTOList;
import com.todomypet.userservice.dto.user.AdminGetAllUsersDTO;
import com.todomypet.userservice.service.AchievementService;
import com.todomypet.userservice.service.SignService;
import com.todomypet.userservice.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
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
    private final AchievementService achievementService;

    @Operation(summary = "업적 추가", description = "업적을 추가합니다. admin 전용 API입니다.")
    @PostMapping("/achievement/add")
    public SuccessResDTO<Void> addAchievement(@RequestHeader String userId,
                                              @RequestBody AddAchievementReqDTOList req) {
        achievementService.addAchievement(req);
        return new SuccessResDTO<Void>(null);
    }

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
