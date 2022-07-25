package com.musiks.backend.user;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken.Payload;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Collections;

import static java.util.Objects.isNull;


@RestController
public class UserController {
    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    UserRepository userRepository;
    String CLIENT_ID = "456371025061-44c24jcod62qnejc2kp6f8dmj3amlshn.apps.googleusercontent.com";
    GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(
            new NetHttpTransport(), new GsonFactory())
            .setAudience(Collections.singleton(CLIENT_ID)).build();

    @PostMapping("/sign-in")
    User signIn(
            @RequestBody String token,
            HttpServletResponse res)
            throws GeneralSecurityException, IOException {

        GoogleIdToken idToken = verifier.verify(token);
        Payload payload = idToken.getPayload();
        String email = payload.getEmail();

        User user = userRepository.findUserByEmail(email);
        res.setStatus(200);

        if (isNull(user)) {
            user = new User();
            user.setEmail(email);
            user.setName((String) payload.get("name"));
            user.setPicture((String) payload.get("picture"));
            userRepository.save(user);
            res.setStatus(201);
        }

        var sessionCookie = new Cookie("session_id", "secret");
        sessionCookie.setHttpOnly(true);
        sessionCookie.setMaxAge(24 * 60 * 60);
        res.addCookie(sessionCookie);

        return user;
    }
}
