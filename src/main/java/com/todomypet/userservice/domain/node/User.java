package com.todomypet.userservice.domain.node;

import lombok.Builder;
import lombok.Getter;
import org.springframework.data.neo4j.core.schema.*;
import org.springframework.data.neo4j.core.support.UUIDStringGenerator;
import org.springframework.security.core.GrantedAuthority;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

@Node("User")
@Getter
@Builder
public class User {
    @Id
    private String id;

    @Property("email")
    private String email;

    @Property("password")
    private String password;

    @Property("nickname")
    private String nickname;

    @Property("bio")
    private String bio;

    @Property("profilePicUrl")
    private String profilePicUrl;

    @Property("personalCode")
    private String personalCode;

    @Property("todoClearCount")
    private Integer todoClearCount;

    @Property("petEvolveCount")
    private Integer petEvolveCount;

    @Property("petCompleteCount")
    private Integer petCompleteCount;

    @Property("achCount")
    private Integer achCount;

    @Property("attendCount")
    private Integer attendCount;

    @Property("attendContinueCount")
    private Integer attendContinueCount;

    @Property("friendCount")
    private Integer friendCount;

    @Property("collectionCount")
    private Integer collectionCount;

    @Property("protected")
    private Boolean Protected;

    @Property("cratedAt")
    private LocalDateTime createdAt;

    @Property("deletedAt")
    private LocalDateTime deletedAt;

    @Property("deleted")
    private Boolean deleted;

    @Property("lastAttendAt")
    private LocalDate lastAttendAt;

    @Property("authority")
    private String authority;
}
