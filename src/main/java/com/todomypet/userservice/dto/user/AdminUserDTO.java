package com.todomypet.userservice.dto.user;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class AdminUserDTO {
    private String id;
    private String email;
    private String password;
    private String nickname;
    private String bio;
    private String profilePicUrl;
    private String oauthKey;
    private String petAcquireCount;
    private String petCompleteCount;
    private String achCount;
    private String attendCount;
    private String attendContinueCount;
    private String friendCount;
    private String collectionCount;
    private boolean Protected;
    private String createdAt;
    private String deletedAt;
    private boolean deleted;
    private String lastAttendAt;
}
