package com.todomypet.userservice.controller;

import com.todomypet.userservice.dto.AddAchievementReqDTO;
import com.todomypet.userservice.dto.AddAchievementResDTO;
import com.todomypet.userservice.dto.SuccessResDTO;
import com.todomypet.userservice.service.AchievementService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/achievement")
@Tag(name = "Achievement Controller")
@SecurityRequirement(name = "bearer-key")
@RequiredArgsConstructor
public class AchievementController {

    private final AchievementService achievementService;

    @Operation(summary = "업적 추가", description = "업적을 추가합니다. admin 전용 API입니다.")
    @PostMapping("/add")
    public SuccessResDTO<AddAchievementResDTO> addAchievement(@RequestBody AddAchievementReqDTO addAchievementReqDTO) {
        AddAchievementResDTO response = AddAchievementResDTO.builder().id(achievementService
                .addAchievement(addAchievementReqDTO)).build();
        return new SuccessResDTO<AddAchievementResDTO>(response);
    }
}
