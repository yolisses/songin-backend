package com.sonhin.backend.mock;

import com.github.javafaker.Faker;
import com.sonhin.backend.artist.Artist;
import com.sonhin.backend.artist.ArtistRepo;
import com.sonhin.backend.comment.Comment;
import com.sonhin.backend.comment.CommentRepo;
import com.sonhin.backend.genre.Genre;
import com.sonhin.backend.genre.GenreRepo;
import com.sonhin.backend.music.Music;
import com.sonhin.backend.music.MusicRepo;
import com.sonhin.backend.user.User;
import com.sonhin.backend.user.UserRepo;
import com.sonhin.backend.user.UserService;
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
    final int genresCount = 100;
    final int userLikesCount = 4;
    final int userSharesCount = 1;
    final int musicGenresCount = 3;
    final int userCommentsCount = 2;
    final int userListenedCount = 10;

    MockRepo mockRepo;
    UserRepo userRepo;
    MusicRepo musicRepo;
    GenreRepo genreRepo;
    ArtistRepo artistRepo;
    UserService userService;
    CommentRepo commentRepo;
    final Faker faker = new Faker();
    final Random random = new Random();

    int randomDuration() {
        var duration = random.nextGaussian() * 60 + 3 * 60;
        return (int) Math.max(duration, 100);
    }

    <Type> Type randomChoice(List<Type> options) {
        return options.get(random.nextInt(options.size()));
    }

    String getCommentText() {
        var value = random.nextDouble();
        double options = 3;
        if (value < 1 / options) return faker.chuckNorris().fact();
        if (value < 2 / options) return faker.hobbit().quote();
        return faker.yoda().quote();
    }

    void addMusics() {
        var musics = new ArrayList<Music>();
        var artists = artistRepo.findAll();
        for (int i = 0; i < musicsCount; i++) {
            var artist = randomChoice(artists);
            var music = new Music();
            music.setMock(true);
            music.setOwner(artist);
            music.setName(faker.book().title());
            music.setDuration(randomDuration());
            var imageOptions = 1080; // images count from lorem picsum
            var imageId = random.nextInt(imageOptions);
            music.setImage("https://picsum.photos/id/" + imageId + "/512/512");
            var audioOptions = 10; // audios count from s3
            var audioId = random.nextInt(audioOptions);
            music.setAudio("https://sonhin.s3.sa-east-1.amazonaws.com/portfolio/" + audioId + ".mp3");
            musics.add(music);
        }
        musicRepo.saveAll(musics);
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
        Set<String> genreNames = new HashSet<>();
        for (int i = 0; i < genresCount; i++) {
            var name = faker.music().genre();
            genreNames.add(name);
        }
        List<Genre> genres = new ArrayList<>();
        for (var name : genreNames) {
            var genre = new Genre();
            genre.setMock(true);
            genre.setName(name);
            genres.add(genre);
        }
        genres = genreRepo.saveAll(genres);
        var musics = musicRepo.findAllByMockTrue();
        for (var music : musics) {
            for (int i = 0; i < musicGenresCount; i++) {
                var genre = randomChoice(genres);
                music.getGenres().add(genre);
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
                var music = randomChoice(musics);
                user.listened.add(music);
            }
            addLikes(user);
            addShares(user);
            addComments(user);
        }
        userRepo.saveAll(users);
    }

    void addUsers() {
        List<User> users = new ArrayList<>();
        for (int i = 0; i < usersCount; i++) {
            User user;
            if (i < artistsCount) {
                user = new Artist();
            } else {
                user = new User();
            }
            user.mock = true;
            user.name = faker.name().fullName();
            user.email = faker.internet().emailAddress();
            users.add(user);
        }
        users = userRepo.saveAll(users);
        for (var user : users) {
            user.image = String.format("https://picsum.photos/id/%d/96/96", user.id);
            user.nick = userService.createNick(user.name);
        }
        userRepo.saveAll(users);
    }

    void addFollowers() {
        var users = userRepo.findAllByMockTrue();
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
        addGenres();
        addListened();
        addFollowers();
    }
}
