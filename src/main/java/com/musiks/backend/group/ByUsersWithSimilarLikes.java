package com.musiks.backend.group;

import com.musiks.backend.artist.ArtistRepo;
import com.musiks.backend.genre.GenreRepo;
import com.musiks.backend.music.Music;
import com.musiks.backend.music.MusicRepo;
import com.musiks.backend.user.User;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ByUsersWithSimilarLikes extends Group<Music> {
    final String type = "by_users_with_similar_likes";

    public void loadMusics(User user, MusicRepo musicRepo, ArtistRepo artistRepo, GenreRepo genreRepo) {
        items = musicRepo.usersThatLikedAsYouLikedThese(user.id);
    }
}
