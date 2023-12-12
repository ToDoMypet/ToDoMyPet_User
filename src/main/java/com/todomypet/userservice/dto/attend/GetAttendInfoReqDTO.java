package com.todomypet.userservice.dto.attend;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class GetAttendInfoReqDTO {
    private int attendCount;
    private int attendContinueCount;
    private int lastAttendanceToToday;
}
