package com.todomypet.userservice.service;

import com.todomypet.userservice.domain.node.Achievement;
import com.todomypet.userservice.domain.node.AchievementType;
import com.todomypet.userservice.domain.relationship.Achieve;
import com.todomypet.userservice.dto.achievement.*;
import com.todomypet.userservice.exception.CustomException;
import com.todomypet.userservice.exception.ErrorCode;
import com.todomypet.userservice.repository.AchieveRepository;
import com.todomypet.userservice.repository.AchievementRepository;
import com.todomypet.userservice.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AchievementServiceImpl implements AchievementService {

    private final AchievementRepository achievementRepository;
    private final AchieveRepository achieveRepository;
    private final UserRepository userRepository;

    @Transactional
    @Override
    public String addAchievement(AddAchievementReqDTO addAchievementReqDTO) {
        Achievement a = Achievement.builder().id(addAchievementReqDTO.getId())
                .achName(addAchievementReqDTO.getAchName())
                .achType(addAchievementReqDTO.getAchType())
                .achDiff(addAchievementReqDTO.getAchDiff())
                .achCondition(addAchievementReqDTO.getAchCondition())
                .achAdvice(addAchievementReqDTO.getAchAdvice())
                .achDescribe(addAchievementReqDTO.getAchDescribe())
                .build();
        return achievementRepository.save(a).getId();
    }

    @Transactional
    @Override
    public void achieve(String userId, AchieveReqDTO achieveReqDTO) {
        achievementRepository.getAchievementById(achieveReqDTO.getAchievementId())
                .orElseThrow(() -> new CustomException(ErrorCode.ACHIEVEMENT_NOT_EXISTS));
        LocalDateTime achievedAt = LocalDateTime.parse(LocalDateTime.now()
                .format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")));

        if (achieveRepository.existsAchieveBetweenUserAndAchievement(userId, achieveReqDTO.getAchievementId())) {
            throw new CustomException(ErrorCode.ALREADY_ACHIEVE);
        };

        userRepository.increaseAchieveCount(userId);

        achieveRepository.createAchieveBetweenUserAndAchievement(userId,
                achieveReqDTO.getAchievementId(), achievedAt.toString());
    }

    @Override
    public GetAchievementListResDTO getAchievementList(String userId) {
        AchievementType[] arr = {AchievementType.ATTENDANCE, AchievementType.ACHIEVE, AchievementType.EVOLUTION,
                AchievementType.GRADUATION, AchievementType.ACQUIREMENT};
        GetAchievementListResDTO getAchievementListResDTO = new GetAchievementListResDTO();

        for (int i = 0; i < arr.length; i++) {
            List<Achievement> achievementList = achievementRepository.getAchievementList(arr[i]);
            List<GetAchievementResDTO> getAchievementResList = new ArrayList<>();
            for (int j = 0; j < achievementList.size(); j++) {
                GetAchievementResDTO getAchievementResDTO = GetAchievementResDTO.builder()
                        .achieved(achieveRepository.existsAchieveBetweenUserAndAchievement(userId,
                                achievementList.get(j).getId()))
                        .id(achievementList.get(j).getId())
                        .achName(achievementList.get(j).getAchName())
                        .build();
                getAchievementResList.add(getAchievementResDTO);
            }
            switch (i) {
                case 0 -> {
                    getAchievementListResDTO.setAttendance(getAchievementResList);
                }
                case 1 -> {
                    getAchievementListResDTO.setAchievement(getAchievementResList);
                }
                case 2 -> {
                    getAchievementListResDTO.setEvolution(getAchievementResList);
                }
                case 3 -> {
                    getAchievementListResDTO.setGraduation(getAchievementResList);
                }
                case 4 -> {
                    getAchievementListResDTO.setAcquirement(getAchievementResList);
                }
            }
        }
        return getAchievementListResDTO;
    }

    @Override
    @Transactional
    public GetAchievementDetailResDTO getAchievementDetail(String userId, String achievementId) {
        Achievement achievement = achievementRepository.getAchievementById(achievementId)
                .orElseThrow(() -> new CustomException(ErrorCode.ACHIEVEMENT_NOT_EXISTS));
        if (!achieveRepository.existsAchieveBetweenUserAndAchievement(userId, achievementId)) {
            throw new CustomException(ErrorCode.DID_NOT_ACHIEVED_ACHIEVEMENT);
        };
        Achieve achieve = achieveRepository.findByUserIdAndAchievementId(userId, achievementId);
        return GetAchievementDetailResDTO.builder()
                .achName(achievement.getAchName())
                .achType(achievement.getAchType())
                .achDescribe(achievement.getAchDescribe())
                .achievedAt(achieve.getAchievedAt().toString())
                .achDiff(achievement.getAchDiff())
                .build();
    }
}
