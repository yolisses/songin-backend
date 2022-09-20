package com.sonhin.backend.artist.mark;

import com.sonhin.backend.user.User;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface MarkRepo extends Neo4jRepository<Mark, String> {
    @Query("match")
    User findUnsignedUser(String id);
}
