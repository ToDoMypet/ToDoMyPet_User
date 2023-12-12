package com.todomypet.userservice.service;

import com.todomypet.userservice.domain.node.User;
import com.todomypet.userservice.dto.attend.GetAttendInfoReqDTO;
import com.todomypet.userservice.exception.CustomException;
import com.todomypet.userservice.exception.ErrorCode;
import com.todomypet.userservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;

@Service
@RequiredArgsConstructor
@Slf4j
public class AttendanceServiceImpl implements AttendanceService {

    private final UserRepository userRepository;

    @Override
    public GetAttendInfoReqDTO getAttendanceInfo(String userId) {
        log.info(">>> 출석 모달 진입:" + userId);
        User user = userRepository.getOneUserById(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_EXISTS));

        Period lastAttendanceToToday = Period.between(user.getLastAttendAt(), LocalDate.now());

        log.info(">>> 휴면 카운트: " + userId + " " + lastAttendanceToToday);

        return GetAttendInfoReqDTO.builder().attendCount(user.getAttendCount())
                .attendContinueCount(user.getAttendContinueCount())
                .lastAttendanceToToday(lastAttendanceToToday.getDays()).build();
    }
}
