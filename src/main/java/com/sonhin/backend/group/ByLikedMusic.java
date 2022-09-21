package com.sonhin.backend.group;

import com.sonhin.backend.artist.ArtistRepo;
import com.sonhin.backend.genre.GenreRepo;
import com.sonhin.backend.music.Music;
import com.sonhin.backend.music.MusicRepo;
import com.sonhin.backend.user.User;

import java.util.Random;

public class ByLikedMusic extends Group<Music> {
    final String type = "by_liked_music";
    final Random random = new Random();

    public void loadMusics(
            User user,
            MusicRepo musicRepo,
            ArtistRepo artistRepo,
            GenreRepo genreRepo) {
        Music music;
        if (user.likes.size() > 0) {
            music = user.likes.get(random.nextInt(user.likes.size()));
        } else {
            var all = musicRepo.findAll();
            music = all.get(random.nextInt(all.size()));
        }
        name += "\"" + music.name + "\"";
        items = musicRepo.usersThatLikedAlsoLikedThese(music.id);
    }
}