package com.todomypet.userservice.controller;

import com.todomypet.userservice.dto.SuccessResDTO;
import com.todomypet.userservice.dto.block.GetBlockListDTO;
import com.todomypet.userservice.service.BlockService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class BlockController {
    private final BlockService blockService;

    @Operation(summary = "유저 차단", description = "유저를 차단합니다. 친구 관계일 시 친구 관계가 삭제되며 다시 친구 추가를 할 수 없습니다.")
    @PostMapping("/block/{targetId}")
    SuccessResDTO<Void> setBlockRelationship(@RequestHeader String userId, @PathVariable String targetId) {
        blockService.setBlockRelationship(userId, targetId);
        return new SuccessResDTO<>(null);
    }

    @Operation(summary = "유저 차단 해제", description = "유저 차단을 해제합니다.")
    @DeleteMapping("/unblock/{targetId}")
    SuccessResDTO<Void> deleteBlockRelationship(@RequestHeader String userId, @PathVariable String targetId) {
        blockService.deleteBlockRelationship(userId, targetId);
        return new SuccessResDTO<>(null);
    }

    @Operation(summary = "차단 목록 조회", description = "차단한 유저 목록을 조회합니다.")
    @GetMapping("/block/list")
    SuccessResDTO<GetBlockListDTO> getBlockList(@RequestHeader String userId) {
        GetBlockListDTO response = blockService.getBlockListByUserId(userId);
        return new SuccessResDTO<GetBlockListDTO>(response);
    }

}
