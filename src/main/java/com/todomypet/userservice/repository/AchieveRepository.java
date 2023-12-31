package com.todomypet.userservice.repository;

import com.todomypet.userservice.domain.relationship.Achieve;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.config.EnableNeo4jRepositories;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.stereotype.Repository;

@Repository
@EnableNeo4jRepositories
public interface AchieveRepository extends Neo4jRepository<Achieve, Long> {
    @Query("MATCH (u:User{id:$userId}) WITH u " +
            "MATCH (a:Achievement{id:$achievementId}) " +
            "CREATE (u)-[:ACHIEVEMENT{achievedAt: $achievedAt}]->(a)")
    void createAchieveBetweenUserAndAchievement(String userId, String achievementId, String achievedAt);

    @Query("MATCH (u:User{id:$userId}) WITH u " +
            "MATCH (a:Achievement{id:$achievementId}) " +
            "RETURN EXISTS((u)-[:ACHIEVE]->(a))")
    boolean existsAchieveBetweenUserAndAchievement(String userId, String achievementId);

    @Query("MATCH (u:User{id:$userId}) WITH u " +
            "MATCH (u)-[ach:ACHIEVE]->(a:Achievement{id:$achievementId}) " +
            "RETURN ach")
    Achieve findByUserIdAndAchievementId(String userId, String achievementId);
}
