package com.todomypet.userservice.repository;

import com.todomypet.userservice.domain.node.User;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.config.EnableNeo4jRepositories;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@EnableNeo4jRepositories
@Repository
public interface UserRepository extends Neo4jRepository<User, String> {
    @Query("MATCH (user:User) WHERE user.id = $userId RETURN user")
    Optional<User> getOneUserById(String userId);

    @Query("MATCH (user:User) WHERE user.email = $email AND user.deleted = false RETURN user")
    Optional<User> getOneUserByEmail(String email);

    @Query("MATCH (user:User) WHERE user.email = $checkedEmail RETURN count(user)")
    Integer getUserCountByEmail(String checkedEmail);

    @Query("MATCH (user:User{personalCode:$personalCode}) " +
            "WHERE user.deleted = false AND  " +
            "RETURN user")
    Optional<User> getOneUserByPersonalCode(@Param("personalCode") String personalCode);

    @Query("MATCH (user:User{id:$userId}) WITH user " +
            "MATCH (user)-[:FRIEND]-(t:User) WHERE t.deleted = false WITH t ORDER BY t.nickname " +
            "RETURN collect(t)")
    List<User> getFriendListByUserId(String userId);

    @Query("MATCH (user:User{id:$userId}) WITH user " +
            "MATCH (user)-[:FRIEND]-(t:User) WHERE t.nickname AND t.deleted = false " +
            "CONTAINS $nickname WITH t ORDER BY t.nickname " +
            "RETURN collect(t)")
    List<User> getFriendListByUserIdAndNickname(String userId, String nickname);

    @Query("MATCH (user:User{id:$userId}) SET user.friendCount = user.friendCount + 1")
    void increaseFriendCount(String userId);

    @Query("MATCH (user:User{id:$userId}) SET user.friendCount = user.friendCount - 1")
    void decreaseFriendCount(String userId);

    @Query("MATCH (user:User{id:$userId}) SET user.deleted = true, user.deletedAt = $deletedAt, " +
            "user.nickname = \"비활성 회원\", user.profilePicUrl = \"\"")
    void deleteAccount(String userId, LocalDateTime deletedAt);

    @Query("MATCH (user:User{id:$userId}) SET user.achCount = user.achCount + 1")
    void increaseAchieveCount(String userId);

    @Query("MATCH (user:User{id:$userId}) SET user.profilePicUrl = $profilePicUrl")
    void updateMyProfileImage(String userId, String profilePicUrl);

    @Query("MATCH (user:User{id:$userId}) SET user.nickname = $nickname, user.bio = $bio, " +
            "user.protected = $protect")
    void updateMyPage(String userId, String nickname, String bio, boolean protect);

    @Query("MATCH (user:User{id:$userId}) RETURN user.password")
    String getPasswordByUserId(String userId);

    @Query("MATCH (user:User{id:$userId}) WITH user MATCH (b:Background{id:\"01\"}) CREATE (user)-[:SELECT]->(b)")
    void setDefaultBackground(String userId);

    @Query("MATCH (user:User{id:$userId}) SET user.attendCount = user.attendCount + 1, " +
            "user.lastAttendAt = $today")
    void updateAttendanceCount(String userId, LocalDate today);

    @Query("MATCH (user:User{id:$userId}) SET user.attendContinueCount = $updateData")
    void updateAttendanceContinueCount(String userId, int updateData);

    @Query("MATCH (u:User{id:$userId}) SET u.collectionCount = u.collectionCount + 1")
    void increaseCollectionCount(String userId);


    @Query("MATCH (u:User{id:$userId}) SET u.petEvolveCount = u.petEvolveCount + 1")
    void increasePetEvolveCount(String userId);

    @Query("MATCH (u:User{id:$userId}) SET u.petCompleteCount = u.petCompleteCount + 1")
    void increasePetCompleteCount(String userId);

    @Query("MATCH (u:User{id:$userId}) WITH u " +
            "CREATE (u)-[:HAVE{colorCode:\"#CBCBCB\", bgCode:\"#F0F0F0\", textCode:\"#909090\"}]->(:Category{id:$categoryId, name:\"미분류\"})")
    void createDefaultCategory(String userId, String categoryId);

    @Query("MATCH (u:User{id:$userId}), (p1:Pet{id:\"101\"}), (p2:Pet{id:\"201\"}), (p3:Pet{id:\"501\"}) " +
            "CREATE (u)-[:AVAILABLE]->(p1), (u)-[:AVAILABLE]->(p2), " +
            "(u)-[:AVAILABLE]->(p3)")
    void setDefaultPet(String userId);

    @Query("MATCH (n:User) RETURN n")
    List<User> getAllUsers();

    @Query("MATCH (n:User{id:$userId}) SET n.todoClearCount = n.todoClearCount + 1")
    void increaseTodoClearCount(String userId);

    @Query("MATCH (n:User{id:$userId}) RETURN n.todoClearCount")
    int getTodoClearCountByUserId(String userId);

    @Query("MATCH (n:User{id:$userId}) RETURN n.petCompleteCount")
    int getPetCompleteCountByUserId(String userId);

    @Query("MATCH (n:User{id:$userId}) RETURN n.petEvolveCount")
    int getPetEvolveCountByUserId(String userId);

    @Query("MATCH (u:User) WHERE u.deleted = true RETURN u")
    List<User> findByDeletedTrue();

    @Query("MATCH (u:User{id:$deletedUserId})-[:FRIEND]-(target:User) SET target.friendCount = target.friendCount - 1")
    void decreaseFriendCountByFriendId(String deletedUserId);

    @Query("MATCH (u:User{email:$email}) SET u.password = $encodedPassword")
    void changePasswordByEmail(String email, String encodedPassword);

    @Query("MATCH (u:User)-[:HAVE]->(:Category)-[:INCLUDE]->(:Todo{id:$todoId}) RETURN u")
    User getUserIdByTodoId(String todoId);

    @Query("MATCH (u:User{id:$userId}) WITH u MATCH (p:Pet) " +
            "WHERE p.petCondition = u.achCount AND p.petGrade = \"BABY\" CREATE (u)-[:AVAILABLE]->(p)")
    void createAvailableByAchieveCondition(String userId);

    @Query("MATCH (u:User{id:$userId}) RETURN u.collectionCount")
    int getCollectionCount(String userId);

    @Query("MATCH (user:User{id:$userId}) WITH user " +
            "MATCH (user)-[:BLOCK]->(t:User) WHERE t.deleted = false " +
            "RETURN collect(t)")
    List<User> getBlockListByUserId(String userId);
}
