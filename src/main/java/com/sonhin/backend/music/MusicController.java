package com.sonhin.backend.music;

import com.sonhin.backend.auth.Auth;
import com.sonhin.backend.comment.Comment;
import com.sonhin.backend.comment.CommentRepo;
import com.sonhin.backend.user.UserRepo;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/musics")
public class MusicController {
    Auth auth;
    UserRepo userRepo;
    MusicRepo musicRepo;
    CommentRepo commentRepo;
    ModelMapper modelMapper;

    @GetMapping("/history")
    List<Music> history(HttpServletRequest req) {
        var user = auth.getUser(req);
        return user.getListened();
    }

    @GetMapping("/feed")
    List<Music> feed(HttpServletRequest req) {
        var user = auth.getUser(req);
        var musics = musicRepo.sortedByAllRelations(user.getId());
        if (musics.size() == 0) {
            musics = musicRepo.mostLiked();
        }
        return musics;
    }

    @GetMapping("/{id}/listen")
    String listen(HttpServletRequest req, @PathVariable Long id) {
        var user = auth.getUser(req);
        var music = musicRepo.findById(id);
        if (music.isEmpty()) throw new ResponseStatusException(
                HttpStatus.NOT_FOUND, "Music not found"
        );
        user.getListened().add(music.get());
        userRepo.save(user);
        return "https://fake-music-path";
    }

    @PostMapping("/{id}/share")
    void share(HttpServletRequest req, @PathVariable Long id) {
        var user = auth.getUser(req);
        var music = musicRepo.findById(id);
        if (music.isEmpty()) throw new ResponseStatusException(
                HttpStatus.NOT_FOUND, "Music not found"
        );
        user.getLikes().add(music.get());
        userRepo.save(user);
    }

    @PostMapping("/{id}/like")
    void like(HttpServletRequest req, @PathVariable Long id) {
        var user = auth.getUser(req);
        var music = musicRepo.findById(id);
        if (music.isEmpty()) throw new ResponseStatusException(
                HttpStatus.NOT_FOUND, "Music not found"
        );
        user.getLikes().add(music.get());
        userRepo.save(user);
    }

    @DeleteMapping("/{id}/like")
    void unlike(HttpServletRequest req, @PathVariable Long id) {
        var user = auth.getUser(req);
        var music = musicRepo.findById(id);
        if (music.isEmpty()) throw new ResponseStatusException(
                HttpStatus.NOT_FOUND, "Music not found"
        );
        user.getLikes().remove(music.get());
        userRepo.save(user);
    }

    @GetMapping("/search")
    List<Music> search(@RequestParam String q) {
        return musicRepo.fulltextSearch(q);
    }

    @GetMapping("/{id}/comments")
    List<Comment> comments(@PathVariable Long id) {
        return commentRepo.findByMusicId(id);
    }

    @PostMapping("/{id}/comments")
    Comment comment(@PathVariable Long id,
                    HttpServletRequest req,
                    @RequestBody String text) {
        var user = auth.getUser(req);
        var music = musicRepo.findById(id);
        if (music.isEmpty()) throw new ResponseStatusException(
                HttpStatus.NOT_FOUND, "Music not found"
        );
        var comment = new Comment();
        comment.setText(text);
        comment.setOwner(user);
        return commentRepo.save(comment);
    }

}
