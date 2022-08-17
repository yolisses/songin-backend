package com.musiks.backend.comment;

import com.musiks.backend.music.Music;
import org.springframework.data.neo4j.repository.Neo4jRepository;

import java.util.List;

public interface CommentRepo extends Neo4jRepository<Comment, Long> {
    List<Comment> findCommentsByRefers(Music refers);
}
