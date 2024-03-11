package com.todomypet.userservice.service;

import com.todomypet.userservice.domain.node.Achievement;
import com.todomypet.userservice.domain.node.AchievementType;
import com.todomypet.userservice.domain.node.User;
import com.todomypet.userservice.domain.relationship.Adopt;
import com.todomypet.userservice.dto.achievement.CheckAchieveOrNotResDTO;
import com.todomypet.userservice.dto.adopt.UpdateExperiencePointReqDTO;
import com.todomypet.userservice.dto.attend.GetAttendInfoReqDTO;
import com.todomypet.userservice.exception.CustomException;
import com.todomypet.userservice.exception.ErrorCode;
import com.todomypet.userservice.repository.AchieveRepository;
import com.todomypet.userservice.repository.AchievementRepository;
import com.todomypet.userservice.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.format.DateTimeFormatter;

@Service
@RequiredArgsConstructor
@Slf4j
public class AttendanceServiceImpl implements AttendanceService {

    private final UserRepository userRepository;
    private final AchievementRepository achievementRepository;
    private final AchieveRepository achieveRepository;
    private final PetServiceClient petServiceClient;

    @Override
    @Transactional
    public GetAttendInfoReqDTO getAttendanceInfo(String userId) {
        log.info(">>> 출석 모달 진입: " + userId);
        User user = userRepository.getOneUserById(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_EXISTS));

        Period lastAttendanceToToday = Period.between(user.getLastAttendAt(), LocalDate.now());

        log.info(">>> 휴면 카운트: " + userId + " " + lastAttendanceToToday);

        return GetAttendInfoReqDTO.builder().attendCount(user.getAttendCount() + 1)
                .attendContinueCount(user.getAttendContinueCount())
                .lastAttendanceToToday(lastAttendanceToToday.getDays()).build();
    }

    @Override
    @Transactional
    public void updateAttendanceCount(String userId) {
        log.info(">>> 출석 일수 갱신 진입: " + userId);

        User user = userRepository.getOneUserById(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_EXISTS));

        int updateData = 1;
        System.out.println(LocalDate.now());
        System.out.println(user.getLastAttendAt());
        System.out.println(Period.between(LocalDate.now(), user.getLastAttendAt()).getDays());

        if ((Period.between(LocalDate.now(), user.getLastAttendAt())).getDays() == -1) {
            updateData = user.getAttendContinueCount() + 1;
            log.info(">>> 연속 출석 처리: " + userId + " " + updateData);
        }

        log.info(">>> 메인 펫 조회: " + userId);
        String petSeq = petServiceClient.getMainPetInfosByUserId(userId).getData().getPetSeq();

        UpdateExperiencePointReqDTO reqDTO = UpdateExperiencePointReqDTO.builder()
                .petSeqId(petSeq)
                .experiencePoint(10)
                .build();

        userRepository.updateAttendanceCount(userId, updateData, LocalDate.now());

        Achievement attendacneAchievement = achievementRepository
                .isSatisfyAchievementCondition(AchievementType.ATTENDANCE, user.getAttendCount());

        if (attendacneAchievement != null) {
            achieveRepository.createAchieveBetweenUserAndAchievement(user.getId(), attendacneAchievement.getId(),
                    LocalDateTime.parse(DateTimeFormatter.ofPattern("YYYY-MM-dd'T'HH:mm:ss")
                            .format(LocalDateTime.now())));
            userRepository.increaseAchieveCount(userId);
        }

        Achievement attendanceContinueAchievement = achievementRepository
                .isSatisfyAchievementCondition(AchievementType.CONTINUE_ATTENDANCE, user.getAttendContinueCount());
        if (attendanceContinueAchievement != null) {
            achieveRepository.createAchieveBetweenUserAndAchievement(user.getId(), attendanceContinueAchievement.getId(),
                    LocalDateTime.parse(DateTimeFormatter.ofPattern("YYYY-MM-dd'T'HH:mm:ss")
                            .format(LocalDateTime.now())));
            userRepository.increaseAchieveCount(userId);
        }

        try {
            petServiceClient.updateExperiencePoint(userId, reqDTO);
        } catch (Exception e) {
            e.printStackTrace();
            throw new CustomException(ErrorCode.FEIGN_CLIENT_ERROR);
        }
    }
}
