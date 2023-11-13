package com.todomypet.userservice.domain.relationship;

import com.todomypet.userservice.domain.node.Achievement;
import org.springframework.data.neo4j.core.schema.RelationshipId;
import org.springframework.data.neo4j.core.schema.RelationshipProperties;
import org.springframework.data.neo4j.core.schema.TargetNode;

import java.time.LocalDateTime;

@RelationshipProperties
public class Achieve {
    @RelationshipId
    private String id;

    private LocalDateTime achievedAt;

    @TargetNode
    private Achievement achievement;
}
