package com.musiks.backend.user;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

@RestController
@AllArgsConstructor
public class UserController {

    @GetMapping
    Object set_user(HttpServletResponse res) {
        var sessionCookie = new Cookie("session_id", "secret");
        sessionCookie.setHttpOnly(true);
        sessionCookie.setMaxAge(24 * 60 * 60);
        res.addCookie(sessionCookie);
        return "foi";
    }
}
