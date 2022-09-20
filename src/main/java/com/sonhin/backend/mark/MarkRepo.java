package com.sonhin.backend.mark;

import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MarkRepo extends Neo4jRepository<Mark, String> {
}
