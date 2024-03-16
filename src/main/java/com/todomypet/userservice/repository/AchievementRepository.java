package com.todomypet.userservice.repository;

import com.todomypet.userservice.domain.node.Achievement;
import com.todomypet.userservice.domain.node.AchievementType;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.config.EnableNeo4jRepositories;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@EnableNeo4jRepositories
@Repository
public interface AchievementRepository extends Neo4jRepository<Achievement, String> {
    @Query("MATCH (a:Achievement{achType:$achievementType}) RETURN a ORDER BY a.id ASC")
    List<Achievement> getAchievementList(AchievementType achievementType);

    @Query("MATCH (a:Achievement{id:$achievementId}) RETURN a")
    Optional<Achievement> getAchievementById(String achievementId);

    @Query("MATCH (a:Achievement{achType:$achievementType, achCondition:$achievementCount}) RETURN a")
    Achievement isSatisfyAchievementCondition(AchievementType achievementType, Integer achievementCount);
}
