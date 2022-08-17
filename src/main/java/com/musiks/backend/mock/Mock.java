package com.musiks.backend.mock;

import com.github.javafaker.Faker;
import com.musiks.backend.comment.Comment;
import com.musiks.backend.comment.CommentRepo;
import com.musiks.backend.music.Music;
import com.musiks.backend.music.MusicRepo;
import com.musiks.backend.user.User;
import com.musiks.backend.user.UserRepo;
import com.musiks.backend.user.UserService;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Configuration;

import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Random;


@Configuration
@AllArgsConstructor
public class Mock {

    MockRepo mockRepo;
    UserRepo userRepo;
    MusicRepo musicRepo;
    UserService userService;
    CommentRepo commentRepo;
    final Random random = new Random();
    final Faker faker = new Faker(new Locale("pt-BR"));

    int randomDuration() {
        var duration = random.nextGaussian() * 60 + 3 * 60;
        return (int) Math.max(duration, 100);
    }

    <Type> Type randomChoice(List<Type> options) {
        return options.get(random.nextInt(options.size()));
    }

    boolean randomChance(double percentage) {
        return random.nextDouble() < percentage;
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

        var usersCount = 10;
        var musicsCount = 50;
        var followsCount = 30;
        var userListenedCount = 10;

        for (var i = 0; i < musicsCount; i++) {
            var music = new Music();
            music.setMock(true);
            music.setName(faker.book().title());
            music.setDuration(randomDuration());
            musicRepo.save(music);
        }

        var musics = musicRepo.findAll();

        for (var i = 0; i < usersCount; i++) {
            var user = new User();
            user.mock = true;
            user.likes = new HashSet<>();
            user.follows = new HashSet<>();
            user.listened = new HashSet<>();
            user.name = faker.name().fullName();
            user.email = faker.internet().emailAddress();
            userRepo.save(user);
            user.image = String.format("https://picsum.photos/id/%d/96/96", user.id);

            for (var j = 0; j < userListenedCount; j++) {
                var music = randomChoice(musics);
                user.listened.add(music);
                if (randomChance(0.4)) {
                    user.likes.add(music);
                }
                if (randomChance(0.1)) {
                    var comment = new Comment();
                    comment.setMock(true);
                    comment.setOwner(user);
                    comment.setRefers(music);
                    comment.setText(getCommentText());
                    commentRepo.save(comment);
                }
                userRepo.save(user);
            }
        }

        var users = userRepo.findAll();
        for (var i = 0; i < followsCount; i++) {
            var follower = randomChoice(users);
            var followed = randomChoice(users);
            if (follower.id != followed.id) {
                follower.follows.add(followed);
                userRepo.save(follower);
            }
        }
    }
}
