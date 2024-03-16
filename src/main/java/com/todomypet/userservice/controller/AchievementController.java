package com.todomypet.userservice.controller;

import com.todomypet.userservice.dto.*;
import com.todomypet.userservice.dto.achievement.*;
import com.todomypet.userservice.service.AchievementService;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/achievement")
@Tag(name = "Achievement Controller")
@SecurityRequirement(name = "bearer-key")
@RequiredArgsConstructor
public class AchievementController {

    private final AchievementService achievementService;

    @Operation(summary = "업적 달성", description = "업적을 달성합니다.")
    @PostMapping("")
    public SuccessResDTO<String> achieve(@Parameter(hidden = true) @RequestHeader String userId, @RequestBody AchieveReqDTO achieveReqDTO) {
        String response = achievementService.achieve(userId, achieveReqDTO);
        return new SuccessResDTO<String>(response);
    }

    @Operation(summary = "투두 업적 달성", description = "ACHIEVE 타입의 업적을 달성합니다.")
    @PostMapping("/todo")
    public SuccessResDTO<Void> achieveTodoAchievement(@RequestHeader String userId) {
        achievementService.achieveTodoAchievement(userId);
        return new SuccessResDTO<Void>(null);
    }

    @Operation(summary = "업적 리스트", description = "마이페이지의 업적 리스트를 불러옵니다.")
    @GetMapping("")
    public SuccessResDTO<GetAchievementListResDTO> getAchievementList(@Parameter(hidden = true) @RequestHeader String userId) {
        GetAchievementListResDTO response = achievementService.getAchievementList(userId);
        return new SuccessResDTO<>(response);
    }

    @Operation(summary = "업적 자세히 보기", description = "업적 리스트에서 업적을 클릭했을 때 출력되는 업적 디테일 정보입니다.")
    @GetMapping("/{achievementId}")
    public SuccessResDTO<GetAchievementDetailResDTO> getAchievementDetail(@Parameter(hidden = true) @RequestHeader String userId,
                                                                          @PathVariable String achievementId) {
        GetAchievementDetailResDTO response = achievementService.getAchievementDetail(userId, achievementId);
        return new SuccessResDTO<GetAchievementDetailResDTO>(response);
    }

    @Hidden
    @PostMapping("/achieve-or-not")
    public SuccessResDTO<CheckAchieveOrNotResDTO> checkAchieveOrNot(@RequestHeader String userId,
                                                    @RequestBody CheckAchieveOrNotReqDTO req) {
        CheckAchieveOrNotResDTO response = achievementService.checkAchievementCondition(userId, req);
        return new SuccessResDTO<CheckAchieveOrNotResDTO>(response);
    }
}
