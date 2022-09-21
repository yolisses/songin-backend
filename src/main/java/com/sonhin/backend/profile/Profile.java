package com.sonhin.backend.profile;

import com.sonhin.backend.user.User;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
public class Profile {
    public User user;
    public boolean following;
    public int followersCounter;
    public int followingCounter;
}