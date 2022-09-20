package com.sonhin.backend.group;

import com.sonhin.backend.artist.ArtistRepo;
import com.sonhin.backend.genre.GenreRepo;
import com.sonhin.backend.music.Music;
import com.sonhin.backend.music.MusicRepo;
import com.sonhin.backend.user.User;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ByUsersWithSimilarLikes extends Group<Music> {
    final String type = "by_users_with_similar_likes";

    public void loadMusics(User user, MusicRepo musicRepo, ArtistRepo artistRepo, GenreRepo genreRepo) {
        items = musicRepo.usersThatLikedAsYouLikedThese(user.getId());
    }
}
