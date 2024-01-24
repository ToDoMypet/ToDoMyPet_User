package com.todomypet.userservice.service;

import com.todomypet.userservice.dto.FeignClientResDTO;
import com.todomypet.userservice.dto.adopt.UpdateExperiencePointReqDTO;
import com.todomypet.userservice.dto.adopt.UpdateExperiencePointResDTO;
import com.todomypet.userservice.dto.pet.GetMainPetInfosResDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name="pet-service", url="${feign.pet.url}")
public interface PetServiceClient {
    @PostMapping(value = "/get-main-pet-infos", consumes = "application/json")
    FeignClientResDTO<GetMainPetInfosResDTO> getMainPetInfosByUserId(@RequestHeader String userId);

    @PutMapping(value = "update-exp", consumes = "application/json")
    FeignClientResDTO<UpdateExperiencePointResDTO> updateExperiencePoint(@RequestHeader String userId,
                                                                         @RequestBody UpdateExperiencePointReqDTO req);
}
