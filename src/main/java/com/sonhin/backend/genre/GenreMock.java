package com.sonhin.backend.genre;


import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;

@Configuration
@AllArgsConstructor
public class GenreMock {
    final String[] genreNames = new String[]{
            "Ambient",
            "Classical",
            "Country",
            "Disco",
            "EDM",
            "Folk",
            "Funk",
            "Gospel",
            "Grime",
            "Grunge",
            "Heavy Metal",
            "Hip-Hop & Rap",
            "House",
            "Indie Rock",
            "Jazz",
            "Latin Music",
            "Pop",
            "Psychedelic Rock",
            "Punk Rock",
            "R&B",
            "Reggae",
            "Rock",
            "Soul",
            "Techno",
            "Trap",
    };
    GenreRepo genreRepo;

    public void addGenres() {
        var genres = new ArrayList<Genre>();
        for (var name : genreNames) {
            var genre = new Genre();
            genres.add(genre);
            genre.name = name;
            genre.mock = true;
        }
        genreRepo.saveAll(genres);
    }
}
