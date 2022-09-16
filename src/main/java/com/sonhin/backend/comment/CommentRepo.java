package com.sonhin.backend.comment;

import com.sonhin.backend.music.Music;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;

import java.util.List;

public interface CommentRepo extends Neo4jRepository<Comment, Long> {
    @Query("match (c:Comment)-[:REFERS]->(m:Music) where id(m)=$musicId return c")
    List<Comment> findByMusicId(Long musicId);
}
