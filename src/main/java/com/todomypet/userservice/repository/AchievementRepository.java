package com.todomypet.userservice.repository;

import com.todomypet.userservice.domain.node.Achievement;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface AchievementRepository extends Neo4jRepository<Achievement, String> {

    @Query("MATCH (u:User{id:$userId}) WITH u " +
            "MATCH (a:Achievement{id:$achievementId}) " +
            "CREATE (u)-[:ACHIEVEMENT{achievedAt: $achievedAt}]->(a)")
    void createAchieveBetweenUserAndAchievement(String userId, String achievementId, String achievedAt);

    @Query("MATCH (u:User{id:$userId}) WITH u " +
            "MATCH (a:Achievement{id:$achievementId}) " +
            "EXISTS (u)-[:ACHIEVE]->(a)")
    boolean existsAchieveBetweenUserAndAchievement(String userId, String achievementId);
}
