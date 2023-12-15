package com.todomypet.userservice.controller;

import com.todomypet.userservice.dto.background.AddBackgroundReqDTO;
import com.todomypet.userservice.dto.background.BackgroundResDTO;
import com.todomypet.userservice.dto.SuccessResDTO;
import com.todomypet.userservice.service.BackgroundService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@Slf4j
@RequiredArgsConstructor
@SecurityRequirement(name = "bearer-key")
@Tag(name = "Background Controller")
public class BackgroundController {

    private final BackgroundService backgroundService;

    // todo: 권한 설정 필요
    @Operation(summary = "배경 추가", description = "어드민 전용 API입니다.")
    @PostMapping("/background/add")
    public SuccessResDTO<Void> addBackground(@RequestBody AddBackgroundReqDTO addBackgroundReqDTO) {
        backgroundService.addBackground(addBackgroundReqDTO);
        return new SuccessResDTO<>(null);
    }

    @Operation(hidden = true)
    @GetMapping("/background/{backgroundId}")
    public SuccessResDTO<String> getBackgroundUrl(@PathVariable String backgroundId) {
        String response = backgroundService.getBackgroundUrlById(backgroundId);
        return new SuccessResDTO<>(response);
    }

    @Operation(summary = "펫룸 리스트 조회", description = "펫룸 리스트를 조회합니다.")
    @GetMapping("/background")
    public SuccessResDTO<List<BackgroundResDTO>> getBackgroundList(@RequestHeader String userId) {
        List<BackgroundResDTO> response = backgroundService.getBackgroundList();
        return new SuccessResDTO<List<BackgroundResDTO>>(response);
    }

    @Operation(summary = "펫룸 변경", description = "펫룸을 변경합니다.")
    @PutMapping("/background/{backgroundId}")
    public SuccessResDTO<Void> changeMainBackground(@RequestHeader String userId, @PathVariable String backgroundId) {
        backgroundService.changeBackground(userId, backgroundId);
        return new SuccessResDTO<>(null);
    }

}
