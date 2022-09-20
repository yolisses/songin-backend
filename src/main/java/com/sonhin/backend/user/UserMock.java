package com.sonhin.backend.user;

import com.github.javafaker.Faker;
import com.sonhin.backend.artist.Artist;
import com.sonhin.backend.mock.MockRepo;
import com.sonhin.backend.music.MusicRepo;
import com.sonhin.backend.random.RandomUtils;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;


@Configuration
@AllArgsConstructor
public class UserMock {
    Faker faker;
    MockRepo mockRepo;
    UserRepo userRepo;
    MusicRepo musicRepo;
    UserService userService;
    RandomUtils randomUtils;

    String mockImage(int id) {
        return "https://picsum.photos/id/" + id + "/96/96";
    }

    public void addUsers(int count, int artistsCount) {
        List<User> users = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            User user;
            if (i < artistsCount) {
                user = new Artist();
            } else {
                user = new User();
            }
            user.mock = true;
            user.image = mockImage(i);
            user.name = faker.name().fullName();
            user.email = faker.internet().emailAddress();
            users.add(user);
        }
        userRepo.saveAll(users);
    }


    public void addNicks() {
        var users = userRepo.findAllByMockTrue();
        for (var user : users) {
            user.nick = userService.createNick(user.name);
        }
        userRepo.saveAll(users);
    }


    public void addFollowers(int followsCount) {
        var users = userRepo.findAllByMockTrue();
        for (var follower : users) {
            for (int i = 0; i < followsCount; i++) {
                var followed = randomUtils.choice(users);
                if (follower.id != followed.id) {
                    follower.follows.add(followed);
                }
            }
        }
        userRepo.saveAll(users);
    }


    public void addLikes(int count) {
        var users = userRepo.findAllByMockTrue();
        var musics = musicRepo.findAllByMockTrue();
        for (var user : users) {
            for (int i = 0; i < count; i++) {
                var music = randomUtils.choice(musics);
                user.likes.add(music);
                user.listened.add(music);
            }
            userRepo.save(user);
        }
    }

    public void addListened(int count) {
        var users = userRepo.findAllByMockTrue();
        var musics = musicRepo.findAllByMockTrue();
        for (var user : users) {
            for (int i = 0; i < count; i++) {
                var music = randomUtils.choice(musics);
                user.listened.add(music);
            }
        }
        userRepo.saveAll(users);
    }

    public void addShares(int count) {
        var users = userRepo.findAllByMockTrue();
        var musics = musicRepo.findAllByMockTrue();
        for (var user : users) {
            for (int i = 0; i < count; i++) {
                var music = randomUtils.choice(musics);
                user.shares.add(music);
                user.listened.add(music);
            }
            userRepo.save(user);
        }
    }
}
