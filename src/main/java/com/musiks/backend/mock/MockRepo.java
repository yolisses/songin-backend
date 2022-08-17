package com.musiks.backend.mock;

import com.musiks.backend.user.User;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;

public interface MockRepo extends Neo4jRepository<User, Long> {
    @Query("match(a{mock:true})detach delete a")
    public void deleteMock();
}
