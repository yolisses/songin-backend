package com.musiks.backend.group;

import com.musiks.backend.music.MusicRepo;
import com.musiks.backend.user.User;

public class ByHistory extends Group {
    final String type = "by_history";

    public void loadMusics(User user, MusicRepo musicRepo) {
        musics = user.listened;
    }
}
