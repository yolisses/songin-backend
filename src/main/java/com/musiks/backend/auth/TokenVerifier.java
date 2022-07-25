package com.musiks.backend.auth;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import org.springframework.context.annotation.Configuration;

import java.util.Collections;

@Configuration
public class TokenVerifier {
    String CLIENT_ID = "456371025061-44c24jcod62qnejc2kp6f8dmj3amlshn.apps.googleusercontent.com";
    GoogleIdTokenVerifier googleVerifier = new GoogleIdTokenVerifier.Builder(
            new NetHttpTransport(), new GsonFactory())
            .setAudience(Collections.singleton(CLIENT_ID)).build();
}
