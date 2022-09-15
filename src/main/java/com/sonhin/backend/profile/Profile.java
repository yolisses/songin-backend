package com.sonhin.backend.profile;

import com.sonhin.backend.user.User;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Profile {
    User user;
    boolean following;
    int followersCounter;
    int followingCounter;
}