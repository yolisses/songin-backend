package com.musiks.backend.group;

import com.musiks.backend.music.MusicRepo;
import com.musiks.backend.user.User;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ByUsersWithSimilarLikes extends Group {
    final String type = "by_users_with_similar_likes";

    public void loadMusics(MusicRepo musicRepo, User user) {
        musics = musicRepo.usersThatLikedTheSameLikedThese(user.id);
    }
}
