package com.musiks.backend.mock;

import com.github.javafaker.Faker;
import com.musiks.backend.music.Music;
import com.musiks.backend.music.MusicRepo;
import com.musiks.backend.user.User;
import com.musiks.backend.user.UserRepo;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Configuration;

import java.util.Locale;

@Configuration
@AllArgsConstructor
public class Mock {

    UserRepo userRepo;
    MusicRepo musicRepo;

    public void run() {
        Faker faker = new Faker(new Locale("pt-BR"));

        var musicsCount = 3;
        for (var i = 0; i < musicsCount; i++) {
            var music = new Music();
            music.setName(faker.book().title());
            musicRepo.save(music);
        }

        var usersCount = 3;
        for (var i = 0; i < usersCount; i++) {
            var user = new User();
            user.name = faker.name().fullName();
            user.email = faker.internet().emailAddress();
            userRepo.save(user);
            user.image = String.format("https://picsum.photos/id/%d/96/96", user.id);
        }
    }
}
