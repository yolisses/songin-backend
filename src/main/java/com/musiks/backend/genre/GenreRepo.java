package com.musiks.backend.genre;

import org.springframework.data.neo4j.repository.Neo4jRepository;

public interface GenreRepo extends Neo4jRepository<Genre, Long> {
}
