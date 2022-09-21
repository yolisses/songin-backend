package com.sonhin.backend.group;

import com.sonhin.backend.artist.ArtistRepo;
import com.sonhin.backend.auth.Auth;
import com.sonhin.backend.genre.GenreRepo;
import com.sonhin.backend.music.MusicRepo;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/groups")
public class GroupController {
    Auth auth;
    MusicRepo musicRepo;
    GenreRepo genreRepo;
    ArtistRepo artistRepo;

    @GetMapping("/recommend")
    public List<Group> recommend(HttpServletRequest req) {
        var user = auth.getUser(req);
        var groups = new ArrayList<Group>();
        Group group;

        group = new ByUsersWithSimilarLikes();
        group.name = "Other people are liking";
        group.loadMusics(user, musicRepo, artistRepo, genreRepo);
        groups.add(group);

        group = new ByHistory();
        group.name = "Listen again";
        group.loadMusics(user, musicRepo, artistRepo, genreRepo);
        groups.add(group);

        group = new ByLikedMusic();
        group.name = "For who likes ";
        group.loadMusics(user, musicRepo, artistRepo, genreRepo);
        groups.add(group);

        group = new ByLikedMusic();
        group.name = "Just like ";
        group.loadMusics(user, musicRepo, artistRepo, genreRepo);
        groups.add(group);

        group = new ByLikesCount();
        group.name = "The best of the best";
        group.loadMusics(user, musicRepo, artistRepo, genreRepo);
        groups.add(group);

        group = new ByFollowersLikes();
        group.name = "The people who you follows likes";
        group.loadMusics(user, musicRepo, artistRepo, genreRepo);
        groups.add(group);

        group = new ByArtistsYouFollow();
        group.name = "From yours followed artists";
        group.loadMusics(user, musicRepo, artistRepo, genreRepo);
        groups.add(group);

        group = new ByGenre();
        group.name = "The best of ";
        group.loadMusics(user, musicRepo, artistRepo, genreRepo);
        groups.add(group);

        group = new ByGenre();
        group.name = "This is ";
        group.loadMusics(user, musicRepo, artistRepo, genreRepo);
        groups.add(group);

        Collections.shuffle(groups);

        return groups;
    }
}
