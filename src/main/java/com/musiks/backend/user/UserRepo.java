package com.musiks.backend.user;

import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepo extends Neo4jRepository<User, Long> {
    User findByEmail(String email);

    @Query("match (o:User)-[r:FOLLOWS]->(u:User) where id(u) = $id return count(o)")
    int followersCount(long id);

    @Query("match (u:User)-[r:FOLLOWS]->(o:User) where id(u) = $id return count(o)")
    int followingCount(long id);

    @Query("match (u:User)-[r:FOLLOWS]->(o:User) where id(u) = $sourceId and id(o) = $targetId return count(r)>0")
    boolean doFollows(long sourceId, long targetId);
}
