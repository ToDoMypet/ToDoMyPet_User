package com.todomypet.userservice.repository;

import com.todomypet.userservice.domain.node.Achievement;
import com.todomypet.userservice.domain.node.AchievementType;
import com.todomypet.userservice.dto.GetAchievementResDTO;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.config.EnableNeo4jRepositories;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@EnableNeo4jRepositories
@Repository
public interface AchievementRepository extends Neo4jRepository<Achievement, String> {
    @Query("MATCH (a:Achievement{achType:$achievementType}) RETURN a ORDER BY a.id ASC")
    List<Achievement> getAchievementList(AchievementType achievementType);
}