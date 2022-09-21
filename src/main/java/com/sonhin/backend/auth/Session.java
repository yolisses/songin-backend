package com.sonhin.backend.auth;

import com.sonhin.backend.user.User;
import lombok.NoArgsConstructor;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;
import org.springframework.data.neo4j.core.support.UUIDStringGenerator;

import java.time.Instant;
import java.time.temporal.ChronoUnit;


@Node
@NoArgsConstructor
public class Session {
    @Id
    @GeneratedValue(generatorClass = UUIDStringGenerator.class)
    public String id;
    @Relationship
    User user;
    String ip;
    public boolean mock;
    boolean loggedOut;
    Instant createdAt;
    static int daysDuration = 7;

    public Session(User user, String ip) {
        this.ip = ip;
        this.user = user;
        this.createdAt = Instant.now();
    }

    boolean isExpired() {
        var now = Instant.now();
        return createdAt
                .plus(daysDuration, ChronoUnit.DAYS)
                .isBefore(now);
    }
}