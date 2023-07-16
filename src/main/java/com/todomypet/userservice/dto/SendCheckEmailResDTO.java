package com.todomypet.userservice.dto;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class SendCheckEmailResDTO {
    private String checkCode;
}
