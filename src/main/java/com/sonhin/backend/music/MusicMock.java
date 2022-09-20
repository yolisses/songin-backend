package com.sonhin.backend.music;

import com.github.javafaker.Faker;
import com.sonhin.backend.artist.ArtistRepo;
import com.sonhin.backend.genre.GenreRepo;
import com.sonhin.backend.random.RandomUtils;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.Random;


@Configuration
@AllArgsConstructor
public class MusicMock {
    Faker faker;
    Random random;
    GenreRepo genreRepo;
    MusicRepo musicRepo;
    ArtistRepo artistRepo;
    RandomUtils randomUtils;

    int randomDuration() {
        var duration = random.nextGaussian() * 60 + 3 * 60;
        return (int) Math.max(duration, 100);
    }

    public void addMusics(int count) {
        var artists = artistRepo.findAll();
        var musics = new ArrayList<Music>();
        for (int i = 0; i < count; i++) {
            var artist = randomUtils.choice(artists);
            var music = new Music();
            music.mock = true;
            music.owner = artist;
            music.name = faker.book().title();
            music.duration = randomDuration();
            musics.add(music);
        }
        musicRepo.saveAll(musics);
    }

    public void addGenres(int count) {
        var genres = genreRepo.findAll();
        var musics = musicRepo.findAllByMockTrue();
        for (var music : musics) {
            for (int i = 0; i < count; i++) {
                var genre = randomUtils.choice(genres);
                music.genres.add(genre);
            }
        }
        musicRepo.saveAll(musics);
    }
}
