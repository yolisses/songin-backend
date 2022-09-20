package com.sonhin.backend.genre;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.neo4j.ogm.annotation.Index;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Node
@Data
@AllArgsConstructor
@NoArgsConstructor
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
