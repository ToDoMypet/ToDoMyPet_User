package com.todomypet.userservice.service;

import com.todomypet.userservice.dto.attend.GetAttendInfoReqDTO;

public interface AttendanceService {
    GetAttendInfoReqDTO getAttendanceInfo(String userId);

    void updateAttendanceCount(String userId, String today);
}
