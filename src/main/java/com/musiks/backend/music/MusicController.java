package com.musiks.backend.music;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MusicController {
    @GetMapping("/")
    public String get_all() {
        return "Hello world";
    }
}
