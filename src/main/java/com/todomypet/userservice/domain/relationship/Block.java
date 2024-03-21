package com.todomypet.userservice.domain.relationship;

import com.todomypet.userservice.domain.node.User;
import org.springframework.data.neo4j.core.schema.RelationshipId;
import org.springframework.data.neo4j.core.schema.RelationshipProperties;
import org.springframework.data.neo4j.core.schema.TargetNode;

@RelationshipProperties
public class Block {
    @RelationshipId
    private Long id;
    @TargetNode
    private User user;
}
