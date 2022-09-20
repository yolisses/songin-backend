package com.sonhin.backend.auth;

import com.sonhin.backend.user.User;
import com.sonhin.backend.user.UserRepo;
import com.sonhin.backend.user.UserService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.GeneralSecurityException;

@RestController
@AllArgsConstructor
public class AuthController {
    Auth auth;
    UserRepo userRepo;
    GoogleAuth googleAuth;
    SessionRepo sessionRepo;
    UserService userService;

    @PostMapping("/logout")
    void logout(HttpServletRequest req, HttpServletResponse res) {
        var sessionId = auth.getSessionId(req);
        var session = sessionRepo.findSessionById(sessionId);
        session.loggedOut = true;
        sessionRepo.save(session);
        auth.removeSessionCookie(res);
    }

    @PostMapping("/sign-in")
    User signIn(@RequestBody String token,
                HttpServletResponse res,
                HttpServletRequest req)
            throws GeneralSecurityException, IOException {

        var payload = googleAuth.getPayload(token);
        var email = payload.getEmail();

        User user = userRepo.findByEmail(email);

        if (user == null) {
            user = googleAuth.getUser(payload);
            user.nick = userService.createNick(user.name);
            user = googleAuth.getUser(payload);
            userRepo.save(user);
            res.setStatus(201);
        } else {
            res.setStatus(200);
        }

        auth.addSession(user, req, res);
        return user;
    }
}
