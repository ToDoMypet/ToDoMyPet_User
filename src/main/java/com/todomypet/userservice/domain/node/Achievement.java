package com.todomypet.userservice.domain.node;

import lombok.Builder;
import lombok.Getter;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Property;
import org.springframework.data.neo4j.core.schema.Relationship;

import java.util.Set;

@Node("Achievement")
@Builder
@Getter
public class Achievement {

    @Id
    private String id;

    @Property("achName")
    private String achName;

    @Property("achType")
    private AchievementType achType;

    @Property("achDiff")
    private int achDiff;

    @Property("achCondition")
    private int achCondition;

    @Property("achAdvice")
    private String achAdvice;

    @Property("achDescribe")
    private String achDescribe;

    @Relationship(type = "ACHIEVE", direction = Relationship.Direction.INCOMING)
    private Set<User> user;
}
