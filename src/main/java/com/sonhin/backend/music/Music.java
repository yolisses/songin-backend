package com.sonhin.backend.music;


import com.sonhin.backend.artist.Artist;
import com.sonhin.backend.genre.Genre;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.util.Set;

@Node
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PUBLIC)
public class Music {
    boolean mock;

    @Id
    @GeneratedValue
    Long id;

    @NotNull
    @NotBlank
    String name;

    @Relationship
    Artist owner;

    @Relationship
    Set<Genre> genres;

    @NotNull
    @Positive
    int duration; // seconds
}
