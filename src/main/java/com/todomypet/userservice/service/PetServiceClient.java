package com.todomypet.userservice.service;

import com.todomypet.userservice.dto.FeignClientResDTO;
import com.todomypet.userservice.dto.UpdateExpReqDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "pet-service", url = "${feign.pet.url}")
public interface PetServiceClient {
    @PutMapping(value = "/update-exp", consumes = "application/json")
    FeignClientResDTO<Void> updatePetExp(@RequestHeader String userId, @RequestBody UpdateExpReqDTO updateExpReqDTO);

    @GetMapping(value = "/main-pet", consumes = "application/json")
    FeignClientResDTO<String> getMainPet(@RequestHeader String userId);
}
