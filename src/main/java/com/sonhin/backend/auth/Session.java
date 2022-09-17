package com.sonhin.backend.auth;

import com.sonhin.backend.user.User;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;
import org.springframework.data.neo4j.core.support.UUIDStringGenerator;

import java.time.Instant;
import java.time.temporal.ChronoUnit;


@Node
@Data
@NoArgsConstructor
public class Session {
    boolean mock;
    @Id
    @GeneratedValue(generatorClass = UUIDStringGenerator.class)
    private String id;
    @Relationship
    private User user;
    private String ip;
    private boolean loggedOut;
    private Instant createdAt;
    static int daysDuration = 7;

    public Session(User user, String ip) {
        this.ip = ip;
        this.user = user;
        this.createdAt = Instant.now();
    }

    public boolean isExpired() {
        var now = Instant.now();
        return createdAt
                .plus(daysDuration, ChronoUnit.DAYS)
                .isBefore(now);
    }
}