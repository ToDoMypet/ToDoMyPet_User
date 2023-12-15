package com.todomypet.userservice.dto.background;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class BackgroundResDTO {
    private String id;
    private String backgroundImageUrl;
}
