package com.todomypet.userservice.service;

import com.todomypet.userservice.dto.*;
import com.todomypet.userservice.dto.adopt.GraduatePetReqDTO;
import com.todomypet.userservice.dto.adopt.GraduatePetResDTO;
import com.todomypet.userservice.dto.adopt.UpdateExperiencePointReqDTO;
import com.todomypet.userservice.dto.adopt.UpdateExperiencePointResDTO;
import com.todomypet.userservice.dto.pet.*;

import java.util.List;

public interface PetService {
    String addPet(AddPetReqDTO addPetReqDTO);
    void adoptPet(String userId, AdoptPetReqDTO adoptPetReqDTO);
    AdoptedPetResListDTO getAdoptedPetList(String userId);
    GetMyPetInfoResListDTO getMyPetInfo(String userId, String signatureCode);
    PetDetailResDTO getPetDetail(String userId, String seq);
    void renamePet(String userId, RenamePetReqDTO renamePetReqDTO);
    GetPetCollectionListResDTO getPetCollection(String userId);
    List<CommunityPetListResDTO> getCommunityPetList(String userId);
    UpdateExperiencePointResDTO updateExperiencePoint(String userId, UpdateExperiencePointReqDTO updateExperiencePointReqDTO);
    List<GetPetUpgradeChoiceResDTO> getPetUpgradeChoice(String userId, String petId);

    UpgradePetResDTO upgradePet(String userId, UpgradePetReqDTO req);

    GraduatePetResDTO graduatePet(String userId, GraduatePetReqDTO req);
}
