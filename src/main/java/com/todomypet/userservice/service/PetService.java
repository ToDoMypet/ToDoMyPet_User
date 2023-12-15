package com.todomypet.userservice.service;

import com.todomypet.userservice.dto.*;
import com.todomypet.userservice.dto.pet.AddPetReqDTO;
import com.todomypet.userservice.dto.pet.AdoptPetReqDTO;
import com.todomypet.userservice.dto.pet.AdoptedPetResListDTO;
import com.todomypet.userservice.dto.pet.CommunityPetListResDTO;

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
    String getMainPetByUserId(String userId);
}
