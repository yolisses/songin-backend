package com.musiks.backend.user;

import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepo extends Neo4jRepository<User, Long> {
    User findByEmail(String email);

    User findByNickname(String nickname);

    @Query("match (o:User)-[r:FOLLOWS]->(u:User) where id(u) = $id return count(o)")
    int followersCount(long id);

    @Query("match (u:User)-[r:FOLLOWS]->(o:User) where id(u) = $id return count(o)")
    int followingCount(long id);

    @Query("match (u:User)-[r:FOLLOWS]->(o:User) where id(u) = $sourceId and id(o) = $targetId return count(r)>0")
    boolean doFollows(long sourceId, long targetId);

    @Query("match (u:User) where u.nickname=~ '$1(\\d)*'  return count(u)")
    int countByNickname(String nickname);

    @Query("create fulltext index userNames if not exists for (u:User) on each [u.name]")
    public void addNameIndex();

    @Query("call db.index.fulltext.queryNodes('userNames', $name) yield node, score return node, score")
    public List<User> fulltextSearch(String name);

}
