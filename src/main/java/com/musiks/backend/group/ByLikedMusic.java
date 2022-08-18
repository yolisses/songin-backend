package com.musiks.backend.group;

import com.musiks.backend.artist.ArtistRepo;
import com.musiks.backend.music.Music;
import com.musiks.backend.music.MusicRepo;
import com.musiks.backend.user.User;

import java.util.Random;

public class ByLikedMusic extends Group<Music> {
    final String type = "by_liked_music";
    final Random random = new Random();

    public void loadMusics(User user, MusicRepo musicRepo, ArtistRepo artistRepo) {
        Music music;
        if (user.likes.size() > 0) {
            music = user.likes.get(random.nextInt(user.likes.size()));
        } else {
            var all = musicRepo.findAll();
            music = all.get(random.nextInt(all.size()));
        }
        setName(name.concat("\"").concat(music.getName()).concat("\""));
        items = musicRepo.usersThatLikedAlsoLikedThese(music.getId());
    }
}