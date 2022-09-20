package com.sonhin.backend.comment;

import com.sonhin.backend.music.Music;
import com.sonhin.backend.user.User;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;

import javax.validation.constraints.NotBlank;

@Node
@NoArgsConstructor
@AllArgsConstructor
public class Comment {
    boolean mock;

    @Id
    @GeneratedValue
    Long id;

    @NotBlank
    public
    String text;

    @Relationship
    public
    User owner;

    @Relationship
    Music refers;
}
