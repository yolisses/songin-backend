package com.musiks.backend.user;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserService {
    UserRepo userRepo;

    private String createNickname(String name) {
        var nicknameBase = name.replaceAll("\\W", "");
        nicknameBase = nicknameBase.toLowerCase();

        if (nicknameBase.isEmpty()) nicknameBase = "user";

        var count = userRepo.countByNickname(nicknameBase);
        String nickname = nicknameBase;
        if (count > 0) {
            nickname = nicknameBase.concat(String.valueOf(count));
        }

        var tries = 10;
        for (var i = 0; i < tries; i++) {
            var alreadyUsingNickname = userRepo.findByNickname(nickname);
            if (alreadyUsingNickname == null) return nickname;
            count += 1;
            nickname = nicknameBase.concat(String.valueOf(count));
        }
        throw new UsernameNicknameException();
    }

    public User insertUser(User user) {
        user.nickname = createNickname(user.name);
        return userRepo.save(user);
    }
}
