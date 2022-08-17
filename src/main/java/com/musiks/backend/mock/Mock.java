package com.musiks.backend.mock;

import com.github.javafaker.Faker;
import com.musiks.backend.comment.Comment;
import com.musiks.backend.comment.CommentRepo;
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

    MockRepo mockRepo;
    UserRepo userRepo;
    MusicRepo musicRepo;
    CommentRepo commentRepo;
    final Random random = new Random();
    final Faker faker = new Faker(new Locale("pt-BR"));

    int randomDuration() {
        var duration = random.nextGaussian() * 60 + 3 * 60;
        return (int) Math.max(duration, 100);
    }

    String getCommentText() {
        var value = random.nextDouble();
        double options = 3;
        if (value < 1 / options) return faker.chuckNorris().fact();
        if (value < 2 / options) return faker.hobbit().quote();
        return faker.yoda().quote();
    }

    public void run() {
        mockRepo.deleteMock();

        var musicsCount = 50;
        var musics = new ArrayList<Music>();
        for (var i = 0; i < musicsCount; i++) {
            var music = new Music();
            music.setMock(true);
            music.setName(faker.book().title());
            music.setDuration(randomDuration());
            music.setComments(new ArrayList<>());
            musicRepo.save(music);
            musics.add(music);
        }

        var usersCount = 10;
        var users = new ArrayList<User>();
        for (var i = 0; i < usersCount; i++) {
            var user = new User();
            user.mock = true;
            user.likes = new HashSet<>();
            user.listened = new HashSet<>();
            user.name = faker.name().fullName();
            user.email = faker.internet().emailAddress();
            userRepo.save(user);
            user.image = String.format("https://picsum.photos/id/%d/96/96", user.id);
            var listenedCount = random.nextGaussian() * 20 + 20;
            for (var j = 0; j < listenedCount; j++) {
                var music = musics.get(random.nextInt(musics.size()));
                user.listened.add(music);
                if (random.nextDouble() < 0.4) {
                    user.likes.add(music);
                }
                if (random.nextDouble() < 0.2) {
                    var comment = new Comment();
                    comment.setMock(true);
                    comment.setOwner(user);
                    comment.setText(getCommentText());
                    commentRepo.save(comment);
                    music.comments.add(comment);
                    musicRepo.save(music);
                }
            }
            userRepo.save(user);
            users.add(user);
        }

    }
}
