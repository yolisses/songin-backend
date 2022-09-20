package com.sonhin.backend.profile;

import com.sonhin.backend.auth.Auth;
import com.sonhin.backend.music.MusicRepo;
import com.sonhin.backend.user.User;
import com.sonhin.backend.user.UserRepo;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@RestController
@AllArgsConstructor
@RequestMapping("/profile")
public class ProfileController {
    Auth auth;
    UserRepo userRepo;
    MusicRepo musicRepo;

    void validateFound(User user) {
        if (user == null) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "User not found"
            );
        }
    }

    void validateFound(Optional<User> user) {
        if (user.isEmpty()) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "User not found"
            );
        }
    }

    @GetMapping("/nick/{nick}")
    Profile profile(HttpServletRequest req, @PathVariable String nick) {
        var currentUser = auth.getUser(req);
        var user = userRepo.findByNick(nick);
        validateFound(user);
        var id = user.id;
        var following = userRepo.doFollows(currentUser.id, id);
        var followersCount = userRepo.followersCount(id);
        var followingCount = userRepo.followingCount(id);
        return new Profile(
                user,
                following,
                followersCount,
                followingCount
        );
    }


    @GetMapping("/id/{id}")
    Profile profile(HttpServletRequest req, @PathVariable Long id) {
        var currentUser = auth.getUser(req);
        var user = userRepo.findById(id);
        validateFound(user);
        var following = userRepo.doFollows(currentUser.id, user.get().id);
        var followersCount = userRepo.followersCount(id);
        var followingCount = userRepo.followingCount(id);
        return new Profile(
                user.get(),
                following,
                followersCount,
                followingCount
        );
    }
}
