package com.todomypet.userservice.domain.node;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    @Test
    public void getEmail() {
        final User user = User.builder().email("abc@aaa.com")
                .nickname("홍길동")
                .build();
        final String email = user.getEmail();
        assertEquals("abc@aaa.com", email);
    }

    @Test
    void getId() {
    }

    @Test
    void getPassword() {
    }

    @Test
    void getNickname() {
    }

    @Test
    void getBio() {
    }

    @Test
    void getProfilePicUrl() {
    }

    @Test
    void getPersonalCode() {
    }

    @Test
    void getTodoClearCount() {
    }

    @Test
    void getPetEvolveCount() {
    }

    @Test
    void getPetCompleteCount() {
    }

    @Test
    void getAchCount() {
    }

    @Test
    void getAttendCount() {
    }

    @Test
    void getAttendContinueCount() {
    }

    @Test
    void getFriendCount() {
    }

    @Test
    void getCollectionCount() {
    }

    @Test
    void getProtected() {
    }

    @Test
    void getCreatedAt() {
    }

    @Test
    void getDeletedAt() {
    }

    @Test
    void getDeleted() {
    }

    @Test
    void getLastAttendAt() {
    }

    @Test
    void getAuthority() {
    }

    @Test
    void builder() {
    }
}