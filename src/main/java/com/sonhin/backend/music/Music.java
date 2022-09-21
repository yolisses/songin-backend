package com.sonhin.backend.music;


import com.sonhin.backend.artist.Artist;
import com.sonhin.backend.genre.Genre;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
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
public class Music {
    public boolean mock;

    @Id
    @GeneratedValue
    public Long id;

    @NotNull
    @NotBlank
    public String name;

    @Relationship
    public Artist owner;

    @Relationship
    public Set<Genre> genres;

    @NotNull
    @Positive
    public int duration; // seconds
}
