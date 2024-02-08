package com.todomypet.userservice.dto;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class GetUserDetailsDTO {
    private String id;
    private String authority;
}
