package com.musiks.backend.comment;

import org.springframework.data.neo4j.repository.Neo4jRepository;

public interface CommentRepo extends Neo4jRepository<Comment, Long> {
}
