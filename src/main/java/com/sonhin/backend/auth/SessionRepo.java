package com.sonhin.backend.auth;

import org.springframework.data.neo4j.repository.Neo4jRepository;

public interface SessionRepo extends Neo4jRepository<Session, String> {
    Session findSessionById(String id);
}
