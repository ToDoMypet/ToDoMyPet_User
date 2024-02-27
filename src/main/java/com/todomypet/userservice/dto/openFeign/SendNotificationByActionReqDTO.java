package com.todomypet.userservice.dto.openFeign;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SendNotificationByActionReqDTO {
    private String userId;
    private NotificationType type;
    private String senderProfilePicUrl;
    private String senderName;
    private String notificationContent;
    private String notificationDataId;

}
