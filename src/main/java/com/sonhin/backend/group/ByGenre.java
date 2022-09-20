package com.sonhin.backend.group;

import com.sonhin.backend.artist.ArtistRepo;
import com.sonhin.backend.genre.GenreRepo;
import com.sonhin.backend.music.Music;
import com.sonhin.backend.music.MusicRepo;
import com.sonhin.backend.user.User;

import java.util.Random;

public class ByGenre extends Group<Music> {
    final Random random = new Random();

    public void loadMusics(User user, MusicRepo musicRepo, ArtistRepo artistRepo, GenreRepo genreRepo) {
        var genres = genreRepo.findAll();
        var genre = genres.get(random.nextInt(genres.size()));
        setName(name.concat(genre.name));
        items = musicRepo.fromGenre(genre.name);
    }
}
