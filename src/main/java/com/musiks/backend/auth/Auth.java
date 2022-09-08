package com.musiks.backend.auth;

import com.musiks.backend.user.User;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Configuration
@AllArgsConstructor
public class Auth {
    SessionRepo sessionRepo;

    public void addSessionCookie(HttpServletResponse res, String sessionId) {
        var secondsPerDay = 24 * 60 * 60;
        var maxAge = Session.weeksDuration * secondsPerDay;

        var sessionCookie = new Cookie("session_id", sessionId);
        sessionCookie.setPath("/");
        sessionCookie.setHttpOnly(true);
        sessionCookie.setMaxAge(maxAge);
        res.addCookie(sessionCookie);
    }

    private void throwForbidden(String text) {
        throw new ResponseStatusException(
                HttpStatus.FORBIDDEN, text
        );
    }

    public String getSessionId(HttpServletRequest req) {
        Cookie[] cookies = req.getCookies();
        if (cookies != null) {
            for (var cookie : cookies) {
                if (cookie.getName().equals("session_id")) {
                    return cookie.getValue();
                }
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

    public User getUserOrNull(HttpServletRequest req) {
        try {
            return getUser(req);
        } catch (ResponseStatusException e) {
            return null;
        }
    }
}
