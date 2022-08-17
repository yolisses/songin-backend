package com.musiks.backend.search;

import com.musiks.backend.user.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;

import javax.validation.constraints.NotBlank;

@Node
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Search {
    @Id
    @GeneratedValue
    Long id;
    @NotBlank
    String text;
    @Relationship
    User owner;
    boolean mock;
}
