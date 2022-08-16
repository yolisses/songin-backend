package com.musiks.backend.music;

import com.musiks.backend.auth.Auth;
import com.musiks.backend.user.UserRepo;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletRequest;
import java.util.Set;

@RestController
@AllArgsConstructor
@RequestMapping("/musics")
public class MusicController {
    Auth auth;
    UserRepo userRepo;
    MusicRepo musicRepo;
    ModelMapper modelMapper;

    @GetMapping("/history")
    Set<Music> history(HttpServletRequest req) {
        var user = auth.getUser(req);
        return user.listened;
    }

    @GetMapping("/{id}/listen")
    String listen(HttpServletRequest req, @PathVariable long id) {
        var user = auth.getUser(req);
        var music = musicRepo.findById(id);
        if (music.isEmpty()) throw new ResponseStatusException(
                HttpStatus.NOT_FOUND, "Music not found"
        );
        user.listened.add(music.get());
        userRepo.save(user);
        return "https://fake-music-path";
    }

    @PostMapping("/{id}/share")
    void share(HttpServletRequest req, @PathVariable long id) {
        var user = auth.getUser(req);
        var music = musicRepo.findById(id);
        if (music.isEmpty()) throw new ResponseStatusException(
                HttpStatus.NOT_FOUND, "Music not found"
        );
        user.likes.add(music.get());
        userRepo.save(user);
    }

    @PostMapping("/{id}/like")
    void like(HttpServletRequest req, @PathVariable long id) {
        var user = auth.getUser(req);
        var music = musicRepo.findById(id);
        if (music.isEmpty()) throw new ResponseStatusException(
                HttpStatus.NOT_FOUND, "Music not found"
        );
        user.likes.add(music.get());
        userRepo.save(user);
    }

    @DeleteMapping("/{id}/like")
    void unlike(HttpServletRequest req, @PathVariable long id) {
        var user = auth.getUser(req);
        var music = musicRepo.findById(id);
        if (music.isEmpty()) throw new ResponseStatusException(
                HttpStatus.NOT_FOUND, "Music not found"
        );
        user.likes.remove(music.get());
        userRepo.save(user);
    }
}
