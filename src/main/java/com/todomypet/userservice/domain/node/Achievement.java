package com.todomypet.userservice.domain.node;

import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Property;

@Node("Achievement")
public class Achievement {

    @Id
    private String id;

    @Property("achName")
    private String achName;

    @Property("achType")
    private String achType;

    @Property("achDiff")
    private int achDiff;

    @Property("achCondition")
    private int achCondition;

    @Property("achAdvice")
    private String achAdvice;

    @Property("achDescribe")
    private String achDescribe;
}
