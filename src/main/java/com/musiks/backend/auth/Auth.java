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

    public User getUser(HttpServletRequest req) {
        Cookie[] cookies = req.getCookies();
        String sessionId = null;
        for (var cookie : cookies) {
            if (cookie.getName().equals("session_id")) {
                sessionId = cookie.getValue();
                break;
            }
        }
        if (sessionId == null) {
            throw new ResponseStatusException(
                    HttpStatus.FORBIDDEN, "Missing session_id cookie"
            );
        }
        var session = sessionRepo.findSessionById(sessionId);
        if (session == null) {
            throw new ResponseStatusException(
                    HttpStatus.FORBIDDEN, "Session not found"
            );
        }
        var ip = req.getRemoteAddr();
        if (!session.getIp().equals(ip)) {
            throw new ResponseStatusException(
                    HttpStatus.FORBIDDEN, "Invalid origin address to session"
            );
        }
        var now = ZonedDateTime.now();
        var isExpired = session.getCreatedAt()
                .plusWeeks(sessionWeeksDuration).isBefore(now);
        if (isExpired) {
            throw new ResponseStatusException(
                    HttpStatus.FORBIDDEN, "Session expired"
            );
        }
        return session.getUser();
    }
}
