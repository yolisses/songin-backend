package com.sonhin.backend.user;

import com.sonhin.backend.auth.Auth;
import com.sonhin.backend.music.Music;
import com.sonhin.backend.music.MusicRepo;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;

@RestController
@AllArgsConstructor
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

    @GetMapping("{userId}/favorites")
    List<Music> favorites(@PathVariable Long userId) {
        return musicRepo.favorites(userId);
    }

    @PostMapping("/{id}/follow")
    void follow(HttpServletRequest req, @PathVariable Long id) {
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
    void unfollow(HttpServletRequest req, @PathVariable Long id) {
        var currentUser = auth.getUser(req);
        var user = userRepo.findById(id);
        validateFound(user);
        currentUser.follows.remove(user.get());
        userRepo.save(currentUser);
    }
}
