package com.musiks.backend.music;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MusicController {
    @GetMapping("/")
    public Music get_one() {
        return new Music("Something just like this");
    }
}
