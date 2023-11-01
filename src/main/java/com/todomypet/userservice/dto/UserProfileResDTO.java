package com.todomypet.userservice.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class UserProfileResDTO {
    private String id;
    private String nickname;
    private String profilePicUrl;
    private String bio;
    private int collectionCount;
    private int achCount;
    private int friendCount;
    private boolean Protected;
    private boolean friendRelationship;
}
