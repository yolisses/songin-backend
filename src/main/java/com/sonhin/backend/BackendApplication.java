package com.sonhin.backend;

import com.sonhin.backend.mock.Mock;
import com.sonhin.backend.music.MusicRepo;
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
        boolean runMock = true;
        return args -> {
            if (runMock)
                mock.run();
            musicRepo.addNameIndex();
        };
    }
}
