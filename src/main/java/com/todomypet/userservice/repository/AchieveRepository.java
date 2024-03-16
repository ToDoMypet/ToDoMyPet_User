package com.todomypet.userservice.repository;

import com.todomypet.userservice.domain.node.AchievementType;
import com.todomypet.userservice.domain.relationship.Achieve;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.config.EnableNeo4jRepositories;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
@EnableNeo4jRepositories
public interface AchieveRepository extends Neo4jRepository<Achieve, Long> {
    @Query("MATCH (u:User{id:$userId}) WITH u " +
            "MATCH (a:Achievement{id:$achievementId}) " +
            "CREATE (u)-[:ACHIEVE{achievedAt: $achievedAt}]->(a)")
    void createAchieveBetweenUserAndAchievement(String userId, String achievementId, LocalDateTime achievedAt);

    @Query("MATCH (u:User{id:$userId}) WITH u " +
            "MATCH (u)-[ach:ACHIEVE]->(a:Achievement{id:$achievementId}) " +
            "RETURN ach")
    Achieve existsAchieveBetweenUserAndAchievement(String userId, String achievementId);

    @Query("MATCH (u:User{id:$userId}) WITH u " +
            "MATCH (u)-[ach:ACHIEVE]->(a:Achievement{id:$achievementId}) " +
            "RETURN ach{.achievedAt}")
    Achieve findByUserIdAndAchievementId(String userId, String achievementId);

    @Query("MATCH (u:User{id:$userId}) WITH u MATCH (a:ACHIEVEMENT) " +
            "WHERE a.achType = $type AND a.achCondition = u.todoClearCount " +
            "CREATE (u)-[:ACHIEVE{achievedAt: $achievedAt}]->(a)")
    void achieveTodoAchievement(String userId, AchievementType type, LocalDateTime achievedAt);
}
