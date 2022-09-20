package com.sonhin.backend.genre;


import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;

@Configuration
@AllArgsConstructor
public class GenreMock {
    final String[] genreNames = new String[]{
            "A Cappella",
            "Alternative Hip Hop",
            "Alternative",
            "American Folk Revival",
            "Barbershop",
            "Big Band",
            "Bluegrass",
            "Blues",
            "Boogie",
            "Boogie-Woogie",
            "Celtic",
            "Chant",
            "Choral",
            "Classic Country",
            "Classic Rock",
            "Classical",
            "Contemporary Bluegrass",
            "Country Blues",
            "Crunk",
            "Disco",
            "Drum and Bass",
            "Dubstep",
            "Easy Listening",
            "Electronic Dance Music",
            "Electronic Rock",
            "Electronic",
            "Electronica",
            "Electropop",
            "Emo",
            "Eurodance",
            "Flamenco",
            "Folk Punk",
            "Folk",
            "Funk",
            "Garage",
            "Gospel",
            "Grime",
            "Grunge",
            "Hard Rock",
            "Hardcore Hip Hop",
            "Hip Hop",
            "Honky Tonk",
            "House",
            "Hymns",
            "Indie Pop",
            "Indie Rock",
            "Jazz",
            "K-Pop",
            "Lounge",
            "Metal",
            "Modern Country",
            "New Age",
            "New Wave",
            "Opera",
            "Pop Latino",
            "Pop",
            "Progressive Rock",
            "Psychedelic Rock",
            "Punk",
            "R&B",
            "Ragtime",
            "Rap",
            "Reggae",
            "Reggaeton",
            "Salsa",
            "Showtunes/Musical Theater",
            "Ska",
            "Soft Rock",
            "Soul",
            "Swing",
            "Symphony",
            "Techno",
            "Trance",
            "Trap",
            "World"
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
