package com.todomypet.userservice.service;

import com.todomypet.userservice.dto.FeignClientResDTO;
import com.todomypet.userservice.dto.openFeign.SendNotificationByActionReqDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "notification-server", url = "${feign.notification.url}")
public interface NotificationServiceClient {
    @PostMapping(value = "/api/notification", consumes = "application/json")
    FeignClientResDTO<Void> sendNotificationByAction(@RequestHeader String userId,
                                                 SendNotificationByActionReqDTO sendNotificationByActionReqDTO);
}
