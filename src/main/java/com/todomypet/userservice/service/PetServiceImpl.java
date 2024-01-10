package com.todomypet.userservice.service;

import com.github.f4b6a3.ulid.UlidCreator;
import com.todomypet.userservice.domain.node.*;
import com.todomypet.userservice.domain.relationship.Adopt;
import com.todomypet.userservice.dto.*;
import com.todomypet.userservice.dto.adopt.GraduatePetReqDTO;
import com.todomypet.userservice.dto.adopt.GraduatePetResDTO;
import com.todomypet.userservice.dto.adopt.UpdateExperiencePointReqDTO;
import com.todomypet.userservice.dto.adopt.UpdateExperiencePointResDTO;
import com.todomypet.userservice.dto.pet.*;
import com.todomypet.userservice.exception.CustomException;
import com.todomypet.userservice.exception.ErrorCode;
import com.todomypet.userservice.repository.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
@RequiredArgsConstructor
@Slf4j
public class PetServiceImpl implements PetService {

    private final PetRepository petRepository;
    private final AdoptRepository adoptRepository;
    private final UserRepository userRepository;
    private final AchievementRepository achievementRepository;
    private final AchieveRepository achieveRepository;


    @Transactional
    @Override
    public void addPet(AddPetReqDTOList addPetReqDTO) {
        for (AddPetReqDTO req : addPetReqDTO.getPetList()) {
            Pet p = Pet.builder().id(req.getId())
                    .petName(req.getName())
                    .petMaxExperiencePoint(req.getMaxExperience())
                    .petImageUrl(req.getImageUrl())
                    .petPortraitUrl(req.getPortraitUrl())
                    .petGif(req.getGif())
                    .petDescribe(req.getDescribe())
                    .petPersonality(PetPersonalityType.valueOf(req.getPersonality()))
                    .petCondition(req.getPetCondition())
                    .petType(req.getType())
                    .petGrade(req.getGrade())
                    .build();
            petRepository.save(p);
        }
    }

    @Override
    @Transactional
    public void adoptPet(String userId, AdoptPetReqDTO adoptPetReqDTO) {
        log.info(">>> 펫 입양 진입");

        if (adoptRepository.existsAdoptByUserIdAndPetId(userId, adoptPetReqDTO.getPetId())) {
            userRepository.increaseCollectionCount(userId);
        }

        StringBuilder signatureCode = new StringBuilder();
        Random rnd = new Random();

        while (true) {
            for (int i = 0; i < 2; i++) {
                signatureCode.append((char)(rnd.nextInt(26) + 65));
            }
            for (int i = 0; i < 9; i++) {
                signatureCode.append(rnd.nextInt(10));
            }

            if (adoptRepository.getOneAdoptBySignatureCode(signatureCode.toString()).isEmpty()) {
                break;
            }
        }

        userRepository.increasePetAcquireCount(userId);
        adoptRepository.createAdoptBetweenAdoptAndUser(userId, adoptPetReqDTO.getPetId(),
                adoptPetReqDTO.getName(), UlidCreator.getUlid().toString(), signatureCode.toString(),
                adoptPetReqDTO.isRenameOrNot());

    }

    @Override
    public AdoptedPetResListDTO getAdoptedPetList(String userId) {
        List<Adopt> getAdoptList = adoptRepository.getAdoptList(userId);
        List<AdoptedPetResDTO> adoptedPetResDTOList = new ArrayList<AdoptedPetResDTO>();
        for(Adopt adopt : getAdoptList) {
            Pet pet = petRepository.getPetBySeqOfAdopt(adopt.getSeq());
            AdoptedPetResDTO adoptedPetResDTO = AdoptedPetResDTO.builder()
                    .name(adopt.getName())
                    .experiencePoint(adopt.getExperiencePoint())
                    .imageUrl(pet.getPetImageUrl())
                    .grade(pet.getPetGrade())
                    .maxExperiencePoint(pet.getPetMaxExperiencePoint())
                    .signatureCode(adopt.getSignatureCode())
                    .build();
            adoptedPetResDTOList.add(adoptedPetResDTO);
        }
        return AdoptedPetResListDTO.builder().petList(adoptedPetResDTOList).build();
    }

    @Override
    public GetMyPetInfoResListDTO getMyPetInfo(String userId, String signatureCode) {
        List<Adopt> petInfos = adoptRepository.getMyPetInfo(userId, signatureCode);
        List<GetMyPetInfoResDTO> getMyPetInfoResDTOList = new ArrayList<GetMyPetInfoResDTO>();
        for (Adopt adopt : petInfos) {
            Pet pet = petRepository.getPetBySeqOfAdopt(adopt.getSeq());

            GetMyPetInfoResDTO getMyPetInfoResDTO = GetMyPetInfoResDTO.builder()
                    .imageUrl(pet.getPetImageUrl())
                    .name(adopt.getName())
                    .maxExperience(pet.getPetMaxExperiencePoint())
                    .experience(adopt.getExperiencePoint())
                    .grade(pet.getPetGrade())
                    .graduated(adopt.getGraduated())
                    .seq(adopt.getSeq())
                    .build();

            getMyPetInfoResDTOList.add(getMyPetInfoResDTO);
        }
        return GetMyPetInfoResListDTO.builder().petInfoList(getMyPetInfoResDTOList).build();
    }

    @Override
    @Transactional
    public PetDetailResDTO getPetDetail(String userId, String seq) {
        Adopt adopt = adoptRepository.getAdoptBySeq(userId, seq)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_EXISTS_ADOPT_RELATIONSHIP));
        Pet pet = petRepository.getPetBySeqOfAdopt(seq);

        return PetDetailResDTO.builder()
                .grade(pet.getPetGrade())
                .name(adopt.getName())
                .type(pet.getPetType())
                .personality(pet.getPetPersonality())
                .description(pet.getPetDescribe())
                .imageUrl(pet.getPetImageUrl())
                .build();
    }

    @Override
    public void renamePet(String userId, RenamePetReqDTO renamePetReqDTO) {
       List<Adopt> adopt = adoptRepository.getAdoptByUserIdAndSignatureCode(userId, renamePetReqDTO.getSignatureCode());

       if (adopt.isEmpty()) {
           throw new CustomException(ErrorCode.NOT_COLLECT_USER_AND_SIGNATURE_CODE);
       }

        adoptRepository.renamePet(userId, renamePetReqDTO.getSignatureCode(), renamePetReqDTO.getRename());
    }

    @Override
    public GetPetCollectionListResDTO getPetCollection(String userId) {
        PetType[] petTypeList = {PetType.BREAD, PetType.GHOST};
        GetPetCollectionListResDTO getPetCollectionListResDTO = new GetPetCollectionListResDTO();

        for (int i = 0; i < petTypeList.length; i++) {
            List<Pet> petList = petRepository.getPetList(petTypeList[i]);
            List<GetPetCollectionResDTO> getPetCollectionResList = new ArrayList<>();
            for (int j = 0; j < petList.size(); j++) {
                Pet pet = petList.get(j);
                GetPetCollectionResDTO getPetCollectionResDTO = GetPetCollectionResDTO.builder()
                        .id(pet.getId())
                        .petName(pet.getPetName())
                        .imageUrl(pet.getPetImageUrl())
                        .collected(adoptRepository.existsAdoptByUserIdAndPetId(userId, pet.getId()))
                        .describe(pet.getPetDescribe())
                        .personality(pet.getPetPersonality())
                        .grade(pet.getPetGrade())
                        .build();
                getPetCollectionResList.add(getPetCollectionResDTO);
            }
            switch (i) {
                case 0 -> {
                    getPetCollectionListResDTO.setBread(getPetCollectionResList);
                }
                case 1 -> {
                    getPetCollectionListResDTO.setGhost(getPetCollectionResList);
                }
            }
        }
        return getPetCollectionListResDTO;
    }

    @Override
    @Transactional
    public List<CommunityPetListResDTO> getCommunityPetList(String userId) {
        List<Adopt> adoptList = adoptRepository.getCommunityPetList(userId);
        List<CommunityPetListResDTO> communityPetListResDTOList = new ArrayList<>();
        for (Adopt adopt : adoptList) {
            communityPetListResDTOList.add(CommunityPetListResDTO.builder()
                    .id(adopt.getSeq())
                    .petName(adopt.getName())
                    .petImageUrl(petRepository.getPetBySeqOfAdopt(adopt.getSeq()).getPetImageUrl()).build());

        }
        return communityPetListResDTOList;
    }

    @Override
    @Transactional
    public UpdateExperiencePointResDTO updateExperiencePoint(String userId,
                                                             UpdateExperiencePointReqDTO updateExperiencePointReqDTO) {
        log.info(">>> 경험치 획득 진입: (유저)" + userId + "/ (펫 seqId)" +
                updateExperiencePointReqDTO.getPetSeqId() + " (기존 경험치)" + adoptRepository.getExperiencePointBySeqId(userId,
                updateExperiencePointReqDTO.getPetSeqId()));
        if (adoptRepository.getAdoptBySeq(userId, updateExperiencePointReqDTO.getPetSeqId()).isEmpty()) {
            throw new CustomException(ErrorCode.NOT_EXISTS_ADOPT_RELATIONSHIP);
        };

        adoptRepository.updateExperiencePoint(userId, updateExperiencePointReqDTO.getPetSeqId(),
                updateExperiencePointReqDTO.getExperiencePoint());

        int updatedExp = adoptRepository.getExperiencePointBySeqId(userId,
                updateExperiencePointReqDTO.getPetSeqId());

        log.info(">>> 경험치 획득 완료: (유저)" + userId + "/ (펫 seqId)" +
                updateExperiencePointReqDTO.getPetSeqId() + " (갱신 경험치)" + adoptRepository.getExperiencePointBySeqId(userId,
                updateExperiencePointReqDTO.getPetSeqId()));

        return UpdateExperiencePointResDTO.builder().updatedExperiencePoint(updatedExp).build();
    }

    @Override
    public List<GetPetUpgradeChoiceResDTO> getPetUpgradeChoice(String userId, String petId) {
        Pet pet = petRepository.getPetByPetId(petId)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_EXISTS_PET));

        PetGradeType nextGrade = getPetNextGradeType(pet);

        List<Pet> petList = petRepository.getPetByGradeAndType(nextGrade, pet.getPetType());
        List<GetPetUpgradeChoiceResDTO> response = new ArrayList<>();

        for (Pet p: petList) {
            GetPetUpgradeChoiceResDTO getPetUpgradeChoiceResDTO = GetPetUpgradeChoiceResDTO.builder()
                    .petId(p.getId())
                    .petName(p.getPetName())
                    .petImageUrl(p.getPetImageUrl())
                    .petGrade(nextGrade)
                    .getOrNot(adoptRepository.existsAdoptByUserIdAndPetId(userId, p.getId()))
                    .build();
            response.add(getPetUpgradeChoiceResDTO);
        }
        return response;
    }

    @Override
    @Transactional
    public UpgradePetResDTO upgradePet(String userId, UpgradePetReqDTO req) {
        log.info(">>> 펫 진화 진입: (userId)" + userId + " " + "(펫 signatureCode)" + req.getSignatureCode());

        if (adoptRepository.existsAdoptByUserIdAndPetId(userId, req.getPetId())) {
            userRepository.increaseCollectionCount(userId);
        }

        Adopt adopt = adoptRepository.getAdoptBySeq(userId, req.getSeq())
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_EXISTS_ADOPT_RELATIONSHIP));
        adoptRepository.graduatePetBySeq(userId, adopt.getSeq());

        String originName = req.getPetName();
        String currentName = req.getPetName();
        Pet pet = petRepository.getPetBySeqOfAdopt(req.getSeq());

        if (adopt.getExperiencePoint() >= pet.getPetMaxExperiencePoint()) {
            throw new CustomException(ErrorCode.EXPERIENCE_POINT_NOT_SATISFIED);
        }

        if (adopt.isRenameOrNot()) {
            log.info(">>> (" + userId + ") rename check");
            originName = pet.getPetName();
        }

        adoptRepository.createAdoptBetweenAdoptAndUser(userId, req.getPetId(), currentName,
                UlidCreator.getUlid().toString(), adopt.getSignatureCode(), adopt.isRenameOrNot());

        userRepository.increasePetEvolveCount(userId);

        // todo: 로컬 테스트 필요
        User user = userRepository.findById(userId).orElseThrow();
        Achievement achievement = achievementRepository
                .isSatisfyAchievementCondition(AchievementType.EVOLUTION, user.getPetEvolveCount());
        if (achievement != null) {
            achieveRepository.createAchieveBetweenUserAndAchievement(userId, achievement.getId(),
                    String.valueOf(LocalDateTime.parse(DateTimeFormatter.ofPattern("YYYY-MM-dd'T'HH:mm:ss")
                            .format(LocalDateTime.now()))));
        };


        return UpgradePetResDTO.builder().renameOrNot(adopt.isRenameOrNot()).originName(originName)
                .currentName(currentName).petImageUrl(pet.getPetImageUrl()).build();
    }

    @Override
    @Transactional
    public GraduatePetResDTO graduatePet(String userId, GraduatePetReqDTO req) {
        log.info(">>> 펫 졸업 진입: (userId)" + userId + " " + "(펫 seq)" + req.getPetSeq());

        Adopt adopt = adoptRepository.getAdoptBySeq(userId, req.getPetSeq())
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_EXISTS_ADOPT_RELATIONSHIP));

        Pet pet = petRepository.getPetBySeqOfAdopt(req.getPetSeq());

        adoptRepository.graduatePetBySeq(userId, req.getPetSeq());
        userRepository.increasePetCompleteCount(userId);

        return GraduatePetResDTO.builder().petName(adopt.getName()).petImageUrl(pet.getPetImageUrl()).build();
    }

    @Override
    public String getMainPetSeqByUserId(String userId) {
        return adoptRepository.getMainPetByUserId(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_EXISTS_MAIN_PET)).getSeq();
    }

    private static PetGradeType getPetNextGradeType(Pet pet) {
        PetGradeType grade = pet.getPetGrade();

        PetGradeType newGrade = null;
        switch (grade) {
            case BABY -> {
                newGrade = PetGradeType.CHILDREN;
            }
            case CHILDREN -> {
                newGrade = PetGradeType.TEENAGER;
            }
            case TEENAGER -> {
                newGrade = PetGradeType.ADULT;
            }
            default -> {
                throw new CustomException(ErrorCode.USER_NOT_EXISTS);
            }
        }
        return newGrade;
    }
}
