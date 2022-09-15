package com.sonhin.backend.group;

import com.sonhin.backend.artist.ArtistRepo;
import com.sonhin.backend.genre.GenreRepo;
import com.sonhin.backend.music.Music;
import com.sonhin.backend.music.MusicRepo;
import com.sonhin.backend.user.User;

public class ByArtistsYouFollow extends Group<Music> {
    public void loadMusics(User user, MusicRepo musicRepo, ArtistRepo artistRepo, GenreRepo genreRepo) {
        items = musicRepo.fromArtistsYouFollow(user.id);
    }
}
