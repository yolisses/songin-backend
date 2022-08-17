package com.musiks.backend.music;


import com.musiks.backend.artist.Artist;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Node
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Music {
    boolean mock;

    @Id
    @GeneratedValue
    long id;

    @NotNull
    @NotBlank
    String name;

    @Relationship
    Artist owner;

    @NotNull
    @Positive
    int duration; // seconds
}
