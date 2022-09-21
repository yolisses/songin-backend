package com.sonhin.backend.comment;

import com.sonhin.backend.music.Music;
import com.sonhin.backend.user.User;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;

import javax.validation.constraints.NotBlank;

@Node
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PUBLIC)
public class Comment {
    boolean mock;

    @Id
    @GeneratedValue
    Long id;

    @NotBlank
    String text;

    @Relationship
    User owner;

    @Relationship
    Music refers;
}
