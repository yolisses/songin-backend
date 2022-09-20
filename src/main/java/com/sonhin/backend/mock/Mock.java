package com.sonhin.backend.mock;

import com.sonhin.backend.comment.CommentMock;
import com.sonhin.backend.genre.GenreMock;
import com.sonhin.backend.music.MusicMock;
import com.sonhin.backend.random.RandomUtils;
import com.sonhin.backend.user.UserMock;
import com.sonhin.backend.user.UserService;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Configuration;


@Configuration
@AllArgsConstructor
public class Mock {
    MockRepo mockRepo;

    UserMock userMock;
    GenreMock genreMock;
    MusicMock musicMock;
    UserService userService;
    RandomUtils randomUtils;
    CommentMock commentMock;


    final int usersCount = 20;
    final int artistsCount = 5;
    final int musicsCount = 50;
    final int followsCount = 4;
    final int userLikesCount = 4;
    final int userSharesCount = 1;
    final int musicGenresCount = 3;
    final int userCommentsCount = 2;
    final int userListenedCount = 10;

    public void run() {
        mockRepo.deleteMock();
        genreMock.addGenres();
        userMock.addUsers(usersCount, artistsCount);
        userMock.addNicks();
        userMock.addFollowers(followsCount);
        musicMock.addMusics(musicsCount);
        musicMock.addGenres(musicGenresCount);
        userMock.addListened(userListenedCount);
        userMock.addLikes(userLikesCount);
        userMock.addShares(userSharesCount);
        commentMock.addComments(userCommentsCount);
    }
}
