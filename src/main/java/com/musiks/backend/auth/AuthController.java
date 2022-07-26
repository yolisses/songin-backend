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

import static com.musiks.backend.auth.Auth.sessionWeeksDuration;
import static java.util.Objects.isNull;

@RestController
@AllArgsConstructor
public class AuthController {
    UserRepo userRepo;
    TokenVerifier verifier;
    SessionRepo sessionRepo;

    @PostMapping("/sign-in")
    User signIn(@RequestBody String token,
                HttpServletResponse res,
                HttpServletRequest req)
            throws GeneralSecurityException, IOException {

        GoogleIdToken idToken = verifier.googleVerifier.verify(token);
        GoogleIdToken.Payload payload = idToken.getPayload();
        String email = payload.getEmail();

        User user = userRepo.findByEmail(email);
        res.setStatus(200);

        if (isNull(user)) {
            user = new User(
                    email,
                    payload.get("name").toString(),
                    payload.get("picture").toString());
            userRepo.save(user);
            res.setStatus(201);
        }

        var session = new Session(user, req.getRemoteAddr());
        sessionRepo.save(session);

        var sessionCookie = new Cookie(
                "session_id", session.getId());
        sessionCookie.setHttpOnly(true);
        var secondsPerDay = 24 * 60 * 60;
        sessionCookie.setMaxAge(sessionWeeksDuration * secondsPerDay);
        res.addCookie(sessionCookie);
        return user;
    }
}
