package com.sonhin.backend.profile;

import com.sonhin.backend.user.User;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PUBLIC)
public class Profile {
    User user;
    boolean following;
    int followersCounter;
    int followingCounter;
}