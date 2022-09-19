package com.sonhin.backend.user;

import com.github.javafaker.Faker;
import com.sonhin.backend.artist.Artist;
import com.sonhin.backend.mock.MockRepo;
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
    UserService userService;

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
}
