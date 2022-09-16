package com.sonhin.backend.comment;

import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;

import java.util.List;

public interface CommentRepo extends Neo4jRepository<Comment, Long> {
    @Query("match (u:User)<-[:OWNER]-(c:Comment)-[:REFERS]->(m:Music) where id(m)=$musicId return u,c")
    List<Comment> findByMusicId(Long musicId);
}
