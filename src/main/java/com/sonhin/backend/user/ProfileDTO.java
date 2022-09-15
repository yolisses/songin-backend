package com.sonhin.backend.user;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ProfileDTO {
    User user;
    boolean following;
    int followersCounter;
    int followingCounter;
}
