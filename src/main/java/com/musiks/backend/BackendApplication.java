package com.musiks.backend;

import com.musiks.backend.mock.Mock;
import com.musiks.backend.music.MusicRepo;
import com.musiks.backend.user.UserRepo;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@AllArgsConstructor
@SpringBootApplication
public class BackendApplication {
    Mock mock;
    UserRepo userRepo;
    MusicRepo musicRepo;

    public static void main(String[] args) {
        SpringApplication.run(BackendApplication.class, args);
    }

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    @Bean
    CommandLineRunner commandLineRunner() {
        userRepo.addNameIndex();
        musicRepo.addNameIndex();
        boolean runMock = true;
        return args -> {
            if (runMock)
                mock.run();
        };
    }
}
