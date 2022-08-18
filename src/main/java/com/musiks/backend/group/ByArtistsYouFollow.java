package com.musiks.backend.group;

import com.musiks.backend.artist.ArtistRepo;
import com.musiks.backend.music.Music;
import com.musiks.backend.music.MusicRepo;
import com.musiks.backend.user.User;

public class ByArtistsYouFollow extends Group<Music> {
    public void loadMusics(User user, MusicRepo musicRepo, ArtistRepo artistRepo) {
        items = musicRepo.fromArtistsYouFollow(user.id);
    }
}
