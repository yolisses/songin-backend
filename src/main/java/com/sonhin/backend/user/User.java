package com.sonhin.backend.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sonhin.backend.music.Music;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.neo4j.ogm.annotation.Index;
import org.neo4j.ogm.annotation.Required;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;

import javax.validation.constraints.Email;
import java.time.Instant;
import java.util.List;
import java.util.Set;


@Node
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PUBLIC)
public class User {
    boolean mock;

    @Id
    @GeneratedValue
    Long id;

    @Required
    String name;
    String image;

    @Required
    @Index(unique = true)
    String nick;

    @Email
    @JsonIgnore
    @Index(unique = true)
    String email;

    @JsonIgnore
    @Relationship
    List<Music> likes;

    @JsonIgnore
    @Relationship
    Set<User> follows;

    @JsonIgnore
    @Relationship
    Set<Music> shares;

    @JsonIgnore
    @Relationship
    List<Music> listened;

    @CreatedDate
    Instant createdAt;
}
