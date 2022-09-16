package com.sonhin.backend.music;


import com.sonhin.backend.artist.Artist;
import com.sonhin.backend.genre.Genre;
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
import java.util.List;

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

    @Relationship
    List<Genre> genres;
    
    @NotNull
    @Positive
    int duration; // seconds
}
