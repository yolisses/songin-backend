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
        if (user.getLikes().size() > 0) {
            music = user.getLikes().get(random.nextInt(user.getLikes().size()));
        } else {
            var all = musicRepo.findAll();
            music = all.get(random.nextInt(all.size()));
        }
        setName(name.concat("\"").concat(music.getName()).concat("\""));
        items = musicRepo.usersThatLikedAlsoLikedThese(music.getId());
    }
}