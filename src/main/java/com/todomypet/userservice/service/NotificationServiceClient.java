package com.todomypet.userservice.service;

import com.todomypet.userservice.dto.FeignClientResDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name="notification-service", url="${feign.notification.url}")
public interface NotificationServiceClient {

    @PostMapping(value = "/send/by-action", consumes = "application/json")
    FeignClientResDTO<Void> sendNotification(@RequestHeader String userId);

}
