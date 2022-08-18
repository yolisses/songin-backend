package com.musiks.backend.group;

import com.musiks.backend.artist.ArtistRepo;
import com.musiks.backend.genre.GenreRepo;
import com.musiks.backend.music.Music;
import com.musiks.backend.music.MusicRepo;
import com.musiks.backend.user.User;

public class ByHistory extends Group<Music> {
    final String type = "by_history";

    public void loadMusics(User user, MusicRepo musicRepo, ArtistRepo artistRepo, GenreRepo genreRepo) {
        items = user.listened;
    }
}
