package com.todomypet.userservice.service;

import com.todomypet.userservice.domain.node.User;
import com.todomypet.userservice.dto.UpdateExpReqDTO;
import com.todomypet.userservice.dto.attend.GetAttendInfoReqDTO;
import com.todomypet.userservice.exception.CustomException;
import com.todomypet.userservice.exception.ErrorCode;
import com.todomypet.userservice.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;

@Service
@RequiredArgsConstructor
@Slf4j
public class AttendanceServiceImpl implements AttendanceService {

    private final UserRepository userRepository;
    private final PetServiceClient petServiceClient;

    @Override
    public GetAttendInfoReqDTO getAttendanceInfo(String userId) {
        log.info(">>> 출석 모달 진입: " + userId);
        User user = userRepository.getOneUserById(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_EXISTS));

        Period lastAttendanceToToday = Period.between(user.getLastAttendAt(), LocalDate.now());

        log.info(">>> 휴면 카운트: " + userId + " " + lastAttendanceToToday);

        return GetAttendInfoReqDTO.builder().attendCount(user.getAttendCount())
                .attendContinueCount(user.getAttendContinueCount())
                .lastAttendanceToToday(lastAttendanceToToday.getDays()).build();
    }

    @Override
    @Transactional
    public void updateAttendanceCount(String userId, String today) {
        log.info(">>> 출석 일수 갱신 진입: " + userId);

        User user = userRepository.getOneUserById(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_EXISTS));

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        int updateData = 1;

        if ((Period.between(LocalDate.parse(today, formatter), user.getLastAttendAt())).getDays() == 1) {
            updateData = user.getAttendContinueCount() + 1;
            log.info(">>> 연속 출석 처리: " + userId + " " + updateData);
        }

        UpdateExpReqDTO updateExpReqDTO = UpdateExpReqDTO.builder()
                .petSeqId(petServiceClient.getMainPet(userId).getData())
                .experiencePoint(10)
                .build();
        petServiceClient.updatePetExp(userId, updateExpReqDTO);

        userRepository.updateAttendanceCount(userId, updateData, LocalDate.now().toString());
    }
}
