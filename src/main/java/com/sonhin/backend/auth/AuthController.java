package com.sonhin.backend.auth;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
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

import static java.util.Objects.isNull;

@RestController
@AllArgsConstructor
public class AuthController {
    Auth auth;
    UserRepo userRepo;
    TokenVerifier verifier;
    SessionRepo sessionRepo;
    UserService userService;

    @PostMapping("/logout")
    void logout(HttpServletRequest req, HttpServletResponse res) {
        var sessionId = auth.getSessionId(req);
        var session = sessionRepo.findSessionById(sessionId);
        session.setLoggedOut(true);
        sessionRepo.save(session);
        auth.removeSessionCookie(res);
    }

    @PostMapping("/sign-in")
    User signIn(@RequestBody String token,
                HttpServletResponse res,
                HttpServletRequest req)
            throws GeneralSecurityException, IOException {

        GoogleIdToken idToken = verifier.googleVerifier.verify(token);
        GoogleIdToken.Payload payload = idToken.getPayload();
        var email = payload.getEmail();

        User user = userRepo.findByEmail(email);

        if (isNull(user)) {
            user = new User();
            user.email = email;
            user.name = payload.get("name").toString();
            user.image = payload.get("picture").toString();
            user.nickname = userService.createNickname(user.name);
            userRepo.save(user);
            res.setStatus(201);
        } else {
            res.setStatus(200);
        }

        var session = new Session(user, req.getRemoteAddr());
        sessionRepo.save(session);
        auth.addSessionCookie(res, session.getId());
        return user;
    }
}
