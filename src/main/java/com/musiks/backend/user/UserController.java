package com.musiks.backend.user;

import com.musiks.backend.auth.Auth;
import com.musiks.backend.utils.Req;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
@RequestMapping("/users")
public class UserController {
    Auth auth;
    UserRepo userRepo;

    @GetMapping("/me")
    User me(Req req) {
        return auth.getUser(req);
    }
}
