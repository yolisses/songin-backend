package com.musiks.backend.music;

import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MusicRepo extends Neo4jRepository<Music, Long> {
}
