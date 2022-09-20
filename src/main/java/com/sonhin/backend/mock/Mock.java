package com.sonhin.backend.mock;

import com.github.javafaker.Faker;
import com.sonhin.backend.artist.ArtistRepo;
import com.sonhin.backend.comment.Comment;
import com.sonhin.backend.comment.CommentRepo;
import com.sonhin.backend.genre.GenreRepo;
import com.sonhin.backend.music.MusicMock;
import com.sonhin.backend.music.MusicRepo;
import com.sonhin.backend.random.RandomUtils;
import com.sonhin.backend.user.User;
import com.sonhin.backend.user.UserMock;
import com.sonhin.backend.user.UserRepo;
import com.sonhin.backend.user.UserService;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Configuration;

import java.util.Random;


@Configuration
@AllArgsConstructor
public class Mock {
    UserMock userMock;
    MockRepo mockRepo;
    UserRepo userRepo;
    MusicMock musicMock;
    MusicRepo musicRepo;
    GenreRepo genreRepo;
    ArtistRepo artistRepo;
    UserService userService;
    CommentRepo commentRepo;
    RandomUtils randomUtils;
    final Faker faker = new Faker();
    final Random random = new Random();


    String getCommentText() {
        var value = random.nextDouble();
        double options = 3;
        if (value < 1 / options) return faker.chuckNorris().fact();
        if (value < 2 / options) return faker.hobbit().quote();
        return faker.yoda().quote();
    }


    void addLikes(User user) {
        for (int i = 0; i < userLikesCount; i++) {
            var music = user.listened.get(i);
            user.likes.add(music);
        }
        userRepo.save(user);
    }

    void addShares(User user) {
        for (int i = 0; i < userSharesCount; i++) {
            var music = user.listened.get(i);
            user.shares.add(music);
        }
        userRepo.save(user);
    }

    void addGenres() {
        var genres = genreRepo.findAll();
        var musics = musicRepo.findAllByMockTrue();
        for (var music : musics) {
            for (int i = 0; i < musicGenresCount; i++) {
                var genre = randomUtils.choice(genres);
                music.genres.add(genre);
            }
        }
        musicRepo.saveAll(musics);
    }


    void addComments(User user) {
        for (int i = 0; i < userCommentsCount; i++) {
            var music = user.listened.get(i);
            var comment = new Comment();
            comment.setMock(true);
            comment.setOwner(user);
            comment.setRefers(music);
            comment.setText(getCommentText());
            commentRepo.save(comment);
        }
    }

    void addListened() {
        var users = userRepo.findAllByMockTrue();
        var musics = musicRepo.findAllByMockTrue();
        for (User user : users) {
            for (int i = 0; i < userListenedCount; i++) {
                var music = randomUtils.choice(musics);
                user.listened.add(music);
            }
            addLikes(user);
            addShares(user);
            addComments(user);
        }
        userRepo.saveAll(users);
    }


    void addFollowers() {
        var users = userRepo.findAllByMockTrue();
        for (var user : users) {
            for (int i = 0; i < followsCount; i++) {
                var followed = randomUtils.choice(users);
                if (user.id != followed.id) {
                    user.follows.add(followed);
                    userRepo.save(user);
                }
            }
        }
    }

    final int usersCount = 20;
    final int artistsCount = 5;
    final int musicsCount = 50;
    final int followsCount = 4;
    final int genresCount = 100;
    final int userLikesCount = 4;
    final int userSharesCount = 1;
    final int musicGenresCount = 3;
    final int userCommentsCount = 2;
    final int userListenedCount = 10;

    public void run() {
        mockRepo.deleteMock();
        userMock.addUsers(usersCount, artistsCount);
        userMock.addNicks();
        musicMock.addMusics(musicsCount);
//        addGenres();
//        addListened();
//        addFollowers();
    }
}
