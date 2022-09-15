package com.sonhin.backend.auth;

import com.sonhin.backend.user.User;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Configuration
@AllArgsConstructor
public class Auth {
    SessionRepo sessionRepo;
    public static String cookieName = "session_id";

    public String getSessionId(HttpServletRequest req) {
        var cookies = req.getCookies();
        if (cookies != null) {
            for (var cookie : cookies) {
                if (cookie.getName().equals(cookieName)) {
                    return cookie.getValue();
                }
            }
        }
        throwForbidden("Missing " + cookieName + " cookie");
        return null;
    }

    public static void addSessionCookie(HttpServletResponse res, String sessionId) {
        var secondsPerDay = 24 * 60 * 60;
        var maxAge = Session.weeksDuration * secondsPerDay;
        final ResponseCookie responseCookie = ResponseCookie
                .from(cookieName, sessionId)
                .httpOnly(true)
                .path("/")
                .maxAge(maxAge)
                .domain("sonhin.com")
                .sameSite("None")
                .secure(true)
                .build();
        res.addHeader(HttpHeaders.SET_COOKIE, responseCookie.toString());
    }

    public static void removeSessionCookie(HttpServletResponse res) {
        var sessionCookie = new Cookie(cookieName, null);
        sessionCookie.setHttpOnly(true);
        sessionCookie.setMaxAge(0);
        res.addCookie(sessionCookie);
    }

    private void throwForbidden(String text) {
        throw new ResponseStatusException(
                HttpStatus.FORBIDDEN, text
        );
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
