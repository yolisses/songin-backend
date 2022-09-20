package com.sonhin.backend.comment;

import com.github.javafaker.Faker;
import com.sonhin.backend.music.MusicRepo;
import com.sonhin.backend.random.RandomUtils;
import com.sonhin.backend.user.UserRepo;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.Random;

@Configuration
@AllArgsConstructor
public class CommentMock {
    Faker faker;
    Random random;
    UserRepo userRepo;
    MusicRepo musicRepo;
    RandomUtils randomUtils;
    CommentRepo commentRepo;

    String getCommentText() {
        var value = random.nextDouble();
        double options = 3;
        if (value < 1 / options) return faker.chuckNorris().fact();
        if (value < 2 / options) return faker.hobbit().quote();
        return faker.yoda().quote();
    }

    public void addComments(int count) {
        var users = userRepo.findAllByMockTrue();
        var musics = musicRepo.findAllByMockTrue();
        var comments = new ArrayList<Comment>();
        for (var user : users) {
            for (int i = 0; i < count; i++) {
                var music = randomUtils.choice(musics);
                var comment = new Comment();
                comment.mock = true;
                comment.owner = user;
                comment.refers = music;
                comment.text = getCommentText();
                comments.add(comment);
            }
        }
        commentRepo.saveAll(comments);
    }
}
