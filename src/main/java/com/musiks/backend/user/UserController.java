package com.musiks.backend.user;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("user")
public class UserController {
    UserRepository userRepository;

    @GetMapping
    List<User> all() {
        return userRepository.findAll();
    }
}
