package com.musiks.backend.auth;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.musiks.backend.user.User;
import com.musiks.backend.user.UserRepo;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.GeneralSecurityException;

import static java.util.Objects.isNull;

@RestController
@AllArgsConstructor
public class AuthController {
    Auth auth;
    UserRepo userRepo;
    TokenVerifier verifier;
    SessionRepo sessionRepo;

    @PostMapping("/logout")
    void logout(HttpServletRequest req,
                HttpServletResponse res) {
        var sessionId = auth.getSessionId(req);
        var session = sessionRepo.findSessionById(sessionId);
        session.setLoggedOut(true);
        removeSessionCookie(res);
    }

    void removeSessionCookie(HttpServletResponse res) {
        var sessionCookie = new Cookie("session_id", null);
        sessionCookie.setHttpOnly(true);
        sessionCookie.setMaxAge(0);
        res.addCookie(sessionCookie);
    }


    void addSessionCookie(HttpServletResponse res, String sessionId) {
        var secondsPerDay = 24 * 60 * 60;
        var maxAge = Session.weeksDuration * secondsPerDay;

        var sessionCookie = new Cookie("session_id", sessionId);
        sessionCookie.setHttpOnly(true);
        sessionCookie.setMaxAge(maxAge);
        res.addCookie(sessionCookie);
    }

    @PostMapping("/sign-in")
    User signIn(@RequestBody String token,
                HttpServletResponse res,
                HttpServletRequest req)
            throws GeneralSecurityException, IOException {

        GoogleIdToken idToken = verifier.googleVerifier.verify(token);
        GoogleIdToken.Payload payload = idToken.getPayload();
        String email = payload.getEmail();

        User user = userRepo.findByEmail(email);

        if (isNull(user)) {
            user = new User();
            user.email = email;
            user.name = payload.get("name").toString();
            user.image = payload.get("picture").toString();
            userRepo.save(user);
            res.setStatus(201);
        } else {
            res.setStatus(200);
        }

        var session = new Session(user, req.getRemoteAddr());
        sessionRepo.save(session);
        addSessionCookie(res, session.getId());
        return user;
    }
}
