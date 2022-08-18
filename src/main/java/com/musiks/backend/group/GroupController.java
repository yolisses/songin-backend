package com.musiks.backend.group;

import com.musiks.backend.artist.ArtistRepo;
import com.musiks.backend.auth.Auth;
import com.musiks.backend.genre.GenreRepo;
import com.musiks.backend.music.MusicRepo;
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
        group.name = "Outros estão curtindo";
        group.loadMusics(user, musicRepo, artistRepo, genreRepo);
        groups.add(group);

        group = new ByHistory();
        group.name = "Escutar de novo";
        group.loadMusics(user, musicRepo, artistRepo, genreRepo);
        groups.add(group);

        group = new ByLikedMusic();
        group.name = "Pra quem gosta de ";
        group.loadMusics(user, musicRepo, artistRepo, genreRepo);
        groups.add(group);

        group = new ByLikedMusic();
        group.name = "Tipo ";
        group.loadMusics(user, musicRepo, artistRepo, genreRepo);
        groups.add(group);

        group = new ByLikesCount();
        group.name = "As tops das tops";
        group.loadMusics(user, musicRepo, artistRepo, genreRepo);
        groups.add(group);

        group = new ByFollowersLikes();
        group.name = "Selo de qualidade pessoas que você segue";
        group.loadMusics(user, musicRepo, artistRepo, genreRepo);
        groups.add(group);

        group = new ByArtistsYouFollow();
        group.name = "Dos artistas que você segue";
        group.loadMusics(user, musicRepo, artistRepo, genreRepo);
        groups.add(group);

        group = new ByGenre();
        group.name = "O melhor do ";
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
