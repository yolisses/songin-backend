package com.musiks.backend.mock;

import com.github.javafaker.Faker;
import com.musiks.backend.music.Music;
import com.musiks.backend.music.MusicRepo;
import com.musiks.backend.user.User;
import com.musiks.backend.user.UserRepo;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Locale;
import java.util.Random;


@Configuration
@AllArgsConstructor
public class Mock {

    UserRepo userRepo;
    MusicRepo musicRepo;
    final Random random = new Random();
    final Faker faker = new Faker(new Locale("pt-BR"));

    int randomDuration() {
        var duration = random.nextGaussian() * 60 + 3 * 60;
        return (int) Math.max(duration, 100);
    }

    public void run() {

        var musicsCount = 100;
        var musics = new ArrayList<Music>();
        for (var i = 0; i < musicsCount; i++) {
            var music = new Music();
            music.setName(faker.book().title());
            music.setDuration(randomDuration());
            musicRepo.save(music);
            musics.add(music);
        }

        var usersCount = 10;
        var users = new ArrayList<User>();
        for (var i = 0; i < usersCount; i++) {
            var user = new User();
            user.name = faker.name().fullName();
            user.email = faker.internet().emailAddress();
            userRepo.save(user);
            user.image = String.format("https://picsum.photos/id/%d/96/96", user.id);
            userRepo.save(user);

            user.likes = new HashSet<>();
            user.listened = new HashSet<>();
            var listenedCount = random.nextGaussian() * 10 + 3;
            for (var j = 0; j < listenedCount; j++) {
                var music = musics.get(random.nextInt(musics.size()));
                user.listened.add(music);
                if (random.nextDouble() < 0.2) {
                    user.likes.add(music);
                }
            }
            userRepo.save(user);
            users.add(user);
        }

    }
}
