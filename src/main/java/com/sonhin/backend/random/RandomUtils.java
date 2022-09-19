package com.sonhin.backend.random;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.Random;

@Configuration
@AllArgsConstructor
public class RandomUtils {
    Random random;

    public <Type> Type choice(List<Type> options) {
        return options.get(random.nextInt(options.size()));
    }
}
