package com.todomypet.userservice.domain.relationship;

import com.todomypet.userservice.domain.node.Pet;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.springframework.data.neo4j.core.schema.Property;
import org.springframework.data.neo4j.core.schema.RelationshipId;
import org.springframework.data.neo4j.core.schema.RelationshipProperties;
import org.springframework.data.neo4j.core.schema.TargetNode;

import java.time.LocalDateTime;

@RelationshipProperties
@Getter
@Builder
public class Adopt {
    @RelationshipId
    private Long id;

    @Property("name")
    private String name;

    @Property("seq")
    private String seq;

    @Property("graduated")
    private boolean graduated;

    @Property("experiencePoint")
    private int experiencePoint;

    @Property("signatureCode")
    private String signatureCode;

    @Property("renameOrNot")
    private boolean renameOrNot;

    @TargetNode
    private Pet pet;
}
