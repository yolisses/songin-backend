package com.sonhin.backend.auth;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import com.sonhin.backend.user.User;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Collections;

@Configuration
public class GoogleAuth {
    String CLIENT_ID = "456371025061-44c24jcod62qnejc2kp6f8dmj3amlshn.apps.googleusercontent.com";
    GoogleIdTokenVerifier googleVerifier = new GoogleIdTokenVerifier.Builder(
            new NetHttpTransport(), new GsonFactory())
            .setAudience(Collections.singleton(CLIENT_ID))
            .build();

    public GoogleIdToken.Payload getPayload(String token) throws GeneralSecurityException, IOException {
        GoogleIdToken idToken = googleVerifier.verify(token);
        GoogleIdToken.Payload payload = idToken.getPayload();
        return payload;
    }

    public User getUser(GoogleIdToken.Payload payload) {
        var user = new User();
        user.email = payload.getEmail();
        ;
        user.name = payload.get("name").toString();
        user.image = payload.get("picture").toString();
        return user;
    }
}
