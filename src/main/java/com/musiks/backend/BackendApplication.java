package com.musiks.backend;

import com.musiks.backend.mock.Mock;
import com.musiks.backend.search.SearchRepo;
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
    SearchRepo searchRepo;

    public static void main(String[] args) {
        SpringApplication.run(BackendApplication.class, args);
    }

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    @Bean
    CommandLineRunner commandLineRunner() {
        searchRepo.addNameIndex();
        boolean runMock = true;
        return args -> {
            if (runMock)
                mock.run();
        };
    }
}
