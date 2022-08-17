package com.musiks.backend.group;

import com.musiks.backend.auth.Auth;
import com.musiks.backend.music.MusicRepo;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@AllArgsConstructor
@RequestMapping("/groups")
public class GroupController {

    Auth auth;
    MusicRepo musicRepo;

    @GetMapping("/recommend")
    public Group recommend(HttpServletRequest req) {
        var group = new ByUsersWithSimilarLikes();
        var user = auth.getUser(req);
        group.name = "Outros estão curtindo";
        group.loadMusics(musicRepo, user);
        return group;
    }
}
