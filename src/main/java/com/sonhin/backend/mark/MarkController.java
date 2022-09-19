package com.sonhin.backend.mark;

import com.sonhin.backend.user.User;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/fingerprint")
public class MarkController {

    MarkRepo markRepo;

    @PostMapping("/sign-in")
    User signIn(@RequestBody String id) {
        User user = markRepo.findUnsignedUser(id);
        return user;
    }
}
