package com.sonhin.backend.user;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserService {
    UserRepo userRepo;

    public String createNick(String name) {
        var nickBase = name.replaceAll("\\W", "");
        nickBase = nickBase.toLowerCase();

        if (nickBase.isEmpty()) nickBase = "user";

        var count = userRepo.countByNick(nickBase);
        String nick = nickBase;
        if (count > 0) {
            nick = nickBase.concat(String.valueOf(count));
        }

        var tries = 10;
        for (var i = 0; i < tries; i++) {
            var alreadyUsingNick = userRepo.findByNick(nick);
            if (alreadyUsingNick == null) return nick;
            count += 1;
            nick = nickBase.concat(String.valueOf(count));
        }
        throw new UsernameNickException();
    }
}
