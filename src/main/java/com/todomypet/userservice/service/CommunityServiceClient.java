package com.todomypet.userservice.service;

import com.todomypet.userservice.dto.FeignClientResDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name="community-service", url="${feign.community.url}")
public interface CommunityServiceClient {
    @DeleteMapping(value = "/board/delete-all-post", consumes = "application/json")
    FeignClientResDTO<Void> deleteAllPostByUserId(@RequestHeader String userId);

    @DeleteMapping(value = "/board/delete-all-reply", consumes = "application/json")
    FeignClientResDTO<Void> deleteAllReplyByUserId(@RequestHeader String userId);
}
