package com.sonhin.backend.user;

import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepo extends Neo4jRepository<User, Long> {
    User findByEmail(String email);

    User findByNick(String nick);

    User findFirstByMockTrue();

    List<User> findAllByMockTrue();

    @Query("match (o:User)-[r:FOLLOWS]->(u:User) where id(u) = $id return count(o)")
    int followersCount(Long id);

    @Query("match (u:User)-[r:FOLLOWS]->(o:User) where id(u) = $id return count(o)")
    int followingCount(Long id);

    @Query("match (u:User)-[r:FOLLOWS]->(o:User) where id(u) = $sourceId and id(o) = $targetId return count(r)>0")
    boolean doFollows(Long sourceId, Long targetId);

    @Query("match (u:User) where u.nick=~ '$1(\\d)*'  return count(u)")
    int countByNick(String nick);
}
