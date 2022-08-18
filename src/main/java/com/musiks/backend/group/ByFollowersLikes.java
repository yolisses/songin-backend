package com.musiks.backend.group;

import com.musiks.backend.artist.ArtistRepo;
import com.musiks.backend.music.Music;
import com.musiks.backend.music.MusicRepo;
import com.musiks.backend.user.User;

public class ByFollowersLikes extends Group<Music> {
    final String type = "by_followers_likes";

    public void loadMusics(User user, MusicRepo musicRepo, ArtistRepo artistRepo) {
        items = musicRepo.followedUsersLikeThese(user.id);
    }
}
