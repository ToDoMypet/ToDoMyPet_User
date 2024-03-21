package com.todomypet.userservice.repository;

import com.todomypet.userservice.domain.relationship.Block;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.config.EnableNeo4jRepositories;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.stereotype.Repository;

@EnableNeo4jRepositories
@Repository
public interface BlockRepository extends Neo4jRepository<Block, Long> {

    @Query("MATCH (u:User) WITH u " +
            "MATCH (t:User) WHERE u.id = $userId AND t.id = $targetId " +
            "CREATE (u)-[:BLOCK]->(t)")
    void setBlockRelationshipBetweenUserAndUser(String userId, String targetId);

    @Query("MATCH (u:User{id:$userId}) WITH u " +
            "MATCH (u)-[block:BLOCK]-(t:User{id:$targetId}) " +
            "DELETE block")
    void deleteBlockRelationshipBetweenUserAndUser(String userId, String targetId);

    @Query("MATCH (u:User) WHERE u.id = $userId WITH u " +
            "MATCH (t:User) WHERE t.id = $targetId " +
            "RETURN exists((u)-[:BLOCK]-(t))")
    boolean existsBlockByUserAndUser(String userId, String targetId);

    @Query("MATCH (u:User) WHERE u.id = $userId WITH u " +
            "MATCH (t:User) WHERE t.id = $targetId " +
            "RETURN exists((u)-[:BLOCK]->(t))")
    boolean getBlockOrNot(String userId, String targetId);
}
