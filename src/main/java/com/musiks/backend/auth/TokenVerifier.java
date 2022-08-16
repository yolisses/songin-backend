package com.musiks.backend.auth;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import java.util.Collections;

@Configuration
public class TokenVerifier {
    @Value("${google.client-id}")
    String CLIENT_ID;
    GoogleIdTokenVerifier googleVerifier = new GoogleIdTokenVerifier.Builder(
            new NetHttpTransport(), new GsonFactory())
            .setAudience(Collections.singleton(CLIENT_ID))
            .build();
}
