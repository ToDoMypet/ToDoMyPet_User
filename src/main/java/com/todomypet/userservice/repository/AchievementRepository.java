package com.todomypet.userservice.repository;

import com.todomypet.userservice.domain.node.Achievement;
import com.todomypet.userservice.domain.node.AchievementType;
import com.todomypet.userservice.dto.GetAchievementResDTO;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.config.EnableNeo4jRepositories;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.stereotype.Repository;

@EnableNeo4jRepositories
@Repository
public interface AchievementRepository extends Neo4jRepository<Achievement, String> {
    @Query("MATCH (u:User{id:$userId}) WITH u " +
            "MATCH (a:Achievement{achType:$achievementType}) RETURN a.id AS id, " +
            "a.achName AS achName, EXISTS((u)-[:ACHIEVE]->(a)) AS achieved ")
    GetAchievementResDTO getAchievementList(String userId, AchievementType achievementType);
}
