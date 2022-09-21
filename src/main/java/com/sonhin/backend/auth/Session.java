package com.sonhin.backend.auth;

import com.sonhin.backend.user.User;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;
import org.springframework.data.neo4j.core.support.UUIDStringGenerator;

import java.time.Instant;
import java.time.temporal.ChronoUnit;


@Node
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PUBLIC)
public class Session {
    @Id
    @GeneratedValue(generatorClass = UUIDStringGenerator.class)
    String id;
    @Relationship
    User user;
    String ip;
    boolean mock;
    boolean loggedOut;
    Instant createdAt;
    static int daysDuration = 7;

    boolean isExpired() {
        var now = Instant.now();
        return createdAt
                .plus(daysDuration, ChronoUnit.DAYS)
                .isBefore(now);
    }

    public Session(User user, String ip) {
        this.ip = ip;
        this.user = user;
        this.createdAt = Instant.now();
    }
}