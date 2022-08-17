package com.musiks.backend.user;

import com.musiks.backend.auth.Auth;
import com.musiks.backend.music.Music;
import com.musiks.backend.music.MusicRepo;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@RestController
@RequestMapping("/users")
public class UserController {
    Auth auth;
    UserRepo userRepo;
    MusicRepo musicRepo;

    @GetMapping("/me")
    User me(HttpServletRequest req) {
        return auth.getUser(req);
    }

    void validateFound(Optional<User> user) {
        if (user.isEmpty()) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "User not found"
            );
        }
    }


    @GetMapping("/{id}/profile")
    ProfileDTO profile(HttpServletRequest req, @PathVariable long id) {
        var currentUser = auth.getUser(req);
        var user = userRepo.findById(id);
        validateFound(user);
        var following = userRepo.doFollows(currentUser.id, user.get().id);
        var followersCount = userRepo.followersCount(id);
        var followingCount = userRepo.followingCount(id);
        return new ProfileDTO(
                user.get(),
                following,
                followersCount,
                followingCount
        );
    }

    @PostMapping("/{id}/follow")
    void follow(HttpServletRequest req, @PathVariable long id) {
        var currentUser = auth.getUser(req);
        if (currentUser.id == id) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "A user can't follow himself"
            );
        }
        var user = userRepo.findById(id);
        validateFound(user);
        currentUser.follows.add(user.get());
        userRepo.save(currentUser);
    }

    @DeleteMapping("/{id}/follow")
    void unfollow(HttpServletRequest req, @PathVariable long id) {
        var currentUser = auth.getUser(req);
        var user = userRepo.findById(id);
        validateFound(user);
        currentUser.follows.remove(user.get());
        userRepo.save(currentUser);
    }

    @GetMapping("/{id}/feed")
    List<Music> feed(@PathVariable long id) {
        var user = userRepo.findById(id);
        if (user.isEmpty()) throw new ResponseStatusException(
                HttpStatus.NOT_FOUND, "User not found"
        );
        System.out.println(user.get().id);
        return musicRepo.usersThatLikedTheSameLikedThese(user.get().id);
    }
}
