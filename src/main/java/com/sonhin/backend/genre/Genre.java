package com.sonhin.backend.genre;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.neo4j.ogm.annotation.Index;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Node
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PUBLIC)
public class Genre {
    @Id
    @GeneratedValue
    Long id;
    boolean mock;
    @NotNull
    @NotBlank
    @Index(unique = true)
    String name;
}
