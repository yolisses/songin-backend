package com.sonhin.backend.mark;

import com.sonhin.backend.auth.Auth;
import com.sonhin.backend.user.User;
import com.sonhin.backend.user.UserRepo;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashSet;

@RestController
@AllArgsConstructor
@RequestMapping("/mark")
public class MarkController {
    Auth auth;
    MarkRepo markRepo;
    UserRepo userRepo;

    Mark createNewMark(String id) {
        var mark = new Mark();
        mark.visitorId = id;
        mark.identifies = new HashSet<>();
        markRepo.save(mark);
        return mark;
    }

    User getUnsignedUser(Mark mark) {
        for (var user : mark.identifies) {
            if (user.email == null) {
                return user;
            }
        }
        return null;
    }

    User createUnsignedUser(Mark mark) {
        var user = new User();
        userRepo.save(user);
        user.name = "Anonymous" + user.id;
        userRepo.save(user);
        mark.identifies.add(user);
        markRepo.save(mark);
        return user;
    }

    @PostMapping("/sign-in")
    User signIn(@RequestBody String id,
                HttpServletResponse res,
                HttpServletRequest req) {

        Mark mark = markRepo
                .findById(id)
                .orElse(null);

        if (mark == null) {
            mark = createNewMark(id);
        }

        var user = getUnsignedUser(mark);
        if (user == null) {
            user = createUnsignedUser(mark);
        }

        auth.addSession(user, req, res);
        return user;
    }
}
