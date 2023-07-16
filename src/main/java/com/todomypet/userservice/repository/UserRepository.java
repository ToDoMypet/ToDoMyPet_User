package com.todomypet.userservice.repository;

import com.todomypet.userservice.domain.node.User;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends Neo4jRepository<User, String> {
    @Query("MATCH (user:User) WHERE user.id = $userId RETURN user")
    Optional<User> getOneUserById(String userId);

    @Query("MATCH (user:User) WHERE user.personalCode = $personalCode RETURN count(user)")
    Integer getUserCountByPersonalCode(String personalCode);

    @Query("MATCH (user:User) WHERE user.email = $checkedEmail RETURN count(user)")
    Integer getUserCountByEmail(String checkedEmail);
}
