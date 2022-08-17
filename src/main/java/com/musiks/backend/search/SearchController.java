package com.musiks.backend.search;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@AllArgsConstructor
@RequestMapping("/search")
public class SearchController {
    SearchRepo searchRepo;

    @GetMapping("/")
    List<Object> search(@RequestParam String q) {
        return searchRepo.fulltextSearch(q);
    }

    @GetMapping("/user")
    List<Object> searchUser(@RequestParam String q) {
        return searchRepo.fulltextSearchWithType(q, "User");
    }

    @GetMapping("/music")
    List<Object> searchMusic(@RequestParam String q) {
        return searchRepo.fulltextSearchWithType(q, "Music");
    }

}
