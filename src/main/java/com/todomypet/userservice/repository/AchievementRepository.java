package com.todomypet.userservice.repository;

import com.todomypet.userservice.domain.node.Achievement;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.config.EnableNeo4jRepositories;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.stereotype.Repository;

@EnableNeo4jRepositories
@Repository
public interface AchievementRepository extends Neo4jRepository<Achievement, String> {
}
