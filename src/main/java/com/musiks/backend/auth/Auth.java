package com.musiks.backend.auth;

import com.musiks.backend.user.User;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.time.ZonedDateTime;

@Configuration
@AllArgsConstructor
public class Auth {
    static int sessionWeeksDuration = 1;
    SessionRepo sessionRepo;

    private void throwForbidden(String text) {
        throw new ResponseStatusException(
                HttpStatus.FORBIDDEN, text
        );
    }

    String getSessionId(HttpServletRequest req) {
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
        var ip = req.getRemoteAddr();
        if (!session.getIp().equals(ip))
            throwForbidden("Invalid origin address to session");
        var now = ZonedDateTime.now();
        var isExpired = session.getCreatedAt()
                .plusWeeks(sessionWeeksDuration).isBefore(now);
        if (isExpired)
            throwForbidden("Session expired");
        return session.getUser();
    }
}
