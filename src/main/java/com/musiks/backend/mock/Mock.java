package com.musiks.backend.mock;

import com.github.javafaker.Faker;
import com.musiks.backend.artist.Artist;
import com.musiks.backend.artist.ArtistRepo;
import com.musiks.backend.comment.Comment;
import com.musiks.backend.comment.CommentRepo;
import com.musiks.backend.music.Music;
import com.musiks.backend.music.MusicRepo;
import com.musiks.backend.user.User;
import com.musiks.backend.user.UserRepo;
import com.musiks.backend.user.UserService;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Configuration;

import java.util.*;


@Configuration
@AllArgsConstructor
public class Mock {
    final int usersCount = 20;
    final int artistsCount = 5;
    final int musicsCount = 50;
    final int followsCount = 4;
    final int userListenedCount = 10;

    MockRepo mockRepo;
    UserRepo userRepo;
    MusicRepo musicRepo;
    ArtistRepo artistRepo;
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

    void addMusics() {

        var artists = artistRepo.findAll();
        for (int i = 0; i < musicsCount; i++) {
            var artist = randomChoice(artists);
            var music = new Music();
            music.setMock(true);
            music.setOwner(artist);
            music.setName(faker.book().title());
            music.setDuration(randomDuration());
            musicRepo.save(music);
        }
    }

    void addLikes() {
        var users = userRepo.findAll();
        for (var user : users) {
            for (var music : user.listened) {
                if (randomChance(0.4)) {
                    user.likes.add(music);
                }
            }
        }
    }

    void addComments() {
        var users = userRepo.findAll();
        for (var user : users) {
            for (var music : user.listened) {
                if (randomChance(0.1)) {
                    var comment = new Comment();
                    comment.setMock(true);
                    comment.setOwner(user);
                    comment.setRefers(music);
                    comment.setText(getCommentText());
                    commentRepo.save(comment);
                }
            }
        }
    }

    void addListened() {
        var users = userRepo.findAll();
        var musics = musicRepo.findAll();
        for (var user : users) {
            for (int j = 0; j < userListenedCount; j++) {
                var music = randomChoice(musics);
                user.listened.add(music);
            }
            userRepo.save(user);
        }
    }

    void addUsers() {
        for (int i = 0; i < usersCount; i++) {
            User user;
            if (i < artistsCount) {
                user = new Artist();
            } else {
                user = new User();
            }
            user.mock = true;
            user.likes = new ArrayList<>();
            user.follows = new HashSet<>();
            user.listened = new ArrayList<>();
            user.name = faker.name().fullName();
            user.email = faker.internet().emailAddress();
            user.nickname = userService.createNickname(user.name);
            user = userRepo.save(user);
            user.image = String.format("https://picsum.photos/id/%d/96/96", user.id);
            userRepo.save(user);
        }
    }

    void addFollowers() {
        var users = userRepo.findAll();
        for (var user : users) {
            for (int i = 0; i < followsCount; i++) {
                var followed = randomChoice(users);
                if (user.id != followed.id) {
                    user.follows.add(followed);
                    userRepo.save(user);
                }
            }
        }
    }

    public void run() {
        mockRepo.deleteMock();
        addUsers();
        addMusics();
        addListened();
        addLikes();
        addComments();
        addFollowers();
    }
}
