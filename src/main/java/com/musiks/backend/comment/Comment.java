package com.musiks.backend.comment;

import com.musiks.backend.user.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;

import javax.validation.constraints.NotBlank;
import java.util.List;

@Node
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Comment {
    @Id
    @GeneratedValue
    Long id;
    @NotBlank
    String text;
    @Relationship
    User owner;
    @Relationship(direction = Relationship.Direction.INCOMING)
    List<Comment> comments;
}
