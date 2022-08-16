package com.musiks.backend.auth;

import com.musiks.backend.user.User;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

@Configuration
@AllArgsConstructor
public class Auth {
    SessionRepo sessionRepo;

    private void throwForbidden(String text) {
        throw new ResponseStatusException(
                HttpStatus.FORBIDDEN, text
        );
    }

    public String getSessionId(HttpServletRequest req) {
        Cookie[] cookies = req.getCookies();
        for (var cookie : cookies) {
            if (cookie.getName().equals("session_id")) {
                return cookie.getValue();
            }
        }
        throwForbidden("Missing session_id cookie");
        return null;
    }

    public User getUser(HttpServletRequest req) {
        var sessionId = getSessionId(req);
        var session = sessionRepo.findSessionById(sessionId);

        if (session == null)
            throwForbidden("Session not found");

        if (session.isLoggedOut())
            throwForbidden("Session already logged out");

        var ip = req.getRemoteAddr();
        if (!session.getIp().equals(ip))
            throwForbidden("Invalid origin address to session: " + ip);

        if (session.isExpired())
            throwForbidden("Session expired");

        return session.getUser();
    }
}
