package com.sonhin.backend.music;

import com.github.javafaker.Faker;
import com.sonhin.backend.artist.ArtistRepo;
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
    MusicRepo musicRepo;
    ArtistRepo artistRepo;
    RandomUtils randomUtils;

    int randomDuration() {
        var duration = random.nextGaussian() * 60 + 3 * 60;
        return (int) Math.max(duration, 100);
    }

    public void addMusics(int count) {
        var musics = new ArrayList<Music>();
        var artists = artistRepo.findAll();
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
}
