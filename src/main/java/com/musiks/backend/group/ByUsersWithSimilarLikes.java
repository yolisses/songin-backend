package com.musiks.backend.group;

import com.musiks.backend.music.MusicRepo;
import com.musiks.backend.user.User;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ByUsersWithSimilarLikes extends Group {
    final String type = "by_users_with_similar_likes";

    public void loadMusics(User user, MusicRepo musicRepo) {
        musics = musicRepo.usersThatLikedAsYouLikedThese(user.id);
    }
}
