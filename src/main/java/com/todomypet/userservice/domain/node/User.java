package com.todomypet.userservice.domain.node;

import lombok.Getter;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Property;
import org.springframework.data.neo4j.core.support.UUIDStringGenerator;

@Node("User")
@Getter
public class User {

    @Id
    @GeneratedValue(generatorClass = UUIDStringGenerator.class)
    private String id;

    @Property("email")
    private String email;

    @Property("password")
    private String password;

    @Property("nickname")
    private String nickname;

    @Property("bio")
    private String bio;

    @Property("profile_pic_url")
    private String profilePicUrl;

    @Property("oauth_key")
    private String oauthKey;

    @Property("personal_code")
    private String personalCode;

    @Property("protected")
    private boolean isProtected;

    @Property("refresh_token")
    private String refreshToken;
}
