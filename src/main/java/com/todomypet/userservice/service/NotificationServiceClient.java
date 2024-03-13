package com.todomypet.userservice.service;

import com.todomypet.userservice.dto.FeignClientResDTO;
import com.todomypet.userservice.dto.openFeign.SendNotificationByActionReqDTO;
import com.todomypet.userservice.dto.user.LogOutReqDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "notification-server", url = "${feign.notification.url}")
public interface NotificationServiceClient {
    @PostMapping(value = "/send/by-action", consumes = "application/json")
    FeignClientResDTO<Void> sendNotificationByAction(@RequestHeader String userId,
                                                 SendNotificationByActionReqDTO sendNotificationByActionReqDTO);

    @DeleteMapping(value = "/delete-fcm-token")
    FeignClientResDTO<Void> deleteFcmToken(@RequestBody LogOutReqDTO req);
}
