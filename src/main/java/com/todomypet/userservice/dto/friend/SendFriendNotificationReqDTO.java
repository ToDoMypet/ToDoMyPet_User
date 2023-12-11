package com.todomypet.userservice.dto.friend;

import lombok.Builder;

@Builder
public class SendFriendNotificationReqDTO {
    private String userId;
    private String type;
    private String senderProfilePicUrl;
    private String senderName;
    private String notificationDataId;
}
