package com.todomypet.userservice.domain.node;

import lombok.Getter;

@Getter
public enum PetType {
    BREAD("빵"),
    CREAM("크림"),
    OVERCOOKED_BREAD("과발효된 빵"),
    COOKIE("쿠키"),
    GHOST("유령");
    ;

    private final String petType;

    PetType(String petType) {
        this.petType = petType;
    }
}
