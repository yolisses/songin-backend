package com.musiks.backend.music;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;

import javax.validation.constraints.NotBlank;

@Node
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Music {
    @Id
    @GeneratedValue
    private Long id;
    @NotBlank
    private String name;
}
