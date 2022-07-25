package com.musiks.backend.auth;

import com.musiks.backend.user.User;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;
import org.springframework.data.neo4j.core.support.UUIDStringGenerator;

import java.time.ZonedDateTime;


@Node
@Data
@NoArgsConstructor
public class Session {
    @Id
    @GeneratedValue(generatorClass = UUIDStringGenerator.class)
    private String id;
    @Relationship
    private User user;
    private String ip;
    private ZonedDateTime createdAt;

    public Session(User user, String ip) {
        this.ip = ip;
        this.user = user;
        this.createdAt = ZonedDateTime.now();
    }
}