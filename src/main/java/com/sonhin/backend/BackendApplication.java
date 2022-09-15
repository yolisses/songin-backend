package com.sonhin.backend;

import com.sonhin.backend.mock.Mock;
import com.sonhin.backend.music.MusicRepo;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

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
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedOrigins(
                                "http://localhost:3000",
                                "https://sonhin.com"
                        )
                        .allowCredentials(true);
            }
        };
    }

    @Bean
    CommandLineRunner commandLineRunner() {
        musicRepo.addNameIndex();
        boolean runMock = true;
        return args -> {
            if (runMock)
                mock.run();
        };
    }
}
