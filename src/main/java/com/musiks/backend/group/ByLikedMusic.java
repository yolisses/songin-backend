package com.musiks.backend.group;

import com.musiks.backend.music.MusicRepo;
import com.musiks.backend.user.User;

import java.util.Random;

public class ByLikedMusic extends Group {
    final String type = "by_liked_music";
    final Random random = new Random();

    public void loadMusics(User user, MusicRepo musicRepo) {
        var music = user.likes.get(random.nextInt(user.likes.size()));
        setName(name.concat("\"").concat(music.getName()).concat("\""));
        musics = musicRepo.usersThatLikedAlsoLikedThese(music.getId());
    }
}