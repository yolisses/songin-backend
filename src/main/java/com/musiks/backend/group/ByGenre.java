package com.musiks.backend.group;

import com.musiks.backend.artist.ArtistRepo;
import com.musiks.backend.genre.GenreRepo;
import com.musiks.backend.music.Music;
import com.musiks.backend.music.MusicRepo;
import com.musiks.backend.user.User;

import java.util.Random;

public class ByGenre extends Group<Music> {
    final Random random = new Random();

    public void loadMusics(User user, MusicRepo musicRepo, ArtistRepo artistRepo, GenreRepo genreRepo) {
        var genres = genreRepo.findAll();
        var genre = genres.get(random.nextInt(genres.size()));
        setName(name.concat(genre.getName()));
        items = musicRepo.fromGenre(genre.getName());
    }
}
