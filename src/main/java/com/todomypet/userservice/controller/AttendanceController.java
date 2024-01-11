package com.todomypet.userservice.controller;

import com.todomypet.userservice.dto.SuccessResDTO;
import com.todomypet.userservice.dto.attend.GetAttendInfoReqDTO;
import com.todomypet.userservice.dto.attend.UpdateAttendanceCountReqDTO;
import com.todomypet.userservice.service.AchievementService;
import com.todomypet.userservice.service.AttendanceService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/attendance")
@Tag(name = "Attendance Controller")
@SecurityRequirement(name = "bearer-key")
@RequiredArgsConstructor
public class AttendanceController {

    private final AttendanceService attendanceService;
    private final AchievementService achievementService;

    @Operation(summary = "접속 시 출석 데이터 조회", description = "해당 일자 최초 접속 시 모달에 표시될 데이터를 조회합니다.")
    @GetMapping("")
    SuccessResDTO<GetAttendInfoReqDTO> getAttendanceInfo(@RequestHeader String userId) {
        GetAttendInfoReqDTO response = attendanceService.getAttendanceInfo(userId);
        return new SuccessResDTO<GetAttendInfoReqDTO>(response);
    }

    @Operation(summary = "출석 카운트 갱신", description = "출석 카운트를 갱신합니다.")
    @PutMapping("")
    SuccessResDTO<Void> updateAttendanceCount(@RequestHeader String userId,
                                          @RequestBody UpdateAttendanceCountReqDTO req) {
        attendanceService.updateAttendanceCount(userId, req.getToday());
        return new SuccessResDTO<>(null);
    }

}
