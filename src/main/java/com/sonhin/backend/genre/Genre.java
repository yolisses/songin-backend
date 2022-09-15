package com.sonhin.backend.genre;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Node
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Genre {
    boolean mock;

    @Id
    @GeneratedValue
    long id;

    @NotNull
    @NotBlank
    String name;
}
