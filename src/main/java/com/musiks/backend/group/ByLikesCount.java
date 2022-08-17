package com.musiks.backend.group;

import com.musiks.backend.music.MusicRepo;
import com.musiks.backend.user.User;

public class ByLikesCount extends Group {
    final String type = "by_likes_count";

    public void loadMusics(User user, MusicRepo musicRepo) {
        musics = musicRepo.mostLiked();
    }
}
