package com.sonhin.backend.dev;

import com.sonhin.backend.auth.Auth;
import com.sonhin.backend.auth.Session;
import com.sonhin.backend.auth.SessionRepo;
import com.sonhin.backend.user.User;
import com.sonhin.backend.user.UserRepo;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@AllArgsConstructor
@RequestMapping("/dev")
public class DevController {
    Auth auth;
    UserRepo userRepo;
    SessionRepo sessionRepo;

    @GetMapping("/mock-login")
    public User mockLogin(HttpServletRequest req, HttpServletResponse res) {
        var user = userRepo.findFirstByMockTrue();
        var session = new Session(user, req.getRemoteAddr());
        session.setMock(true);
        sessionRepo.save(session);
        auth.addSessionCookie(res, session.getId());
        return user;
    }
}
