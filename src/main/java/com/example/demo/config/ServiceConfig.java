package com.example.demo.config;


import com.example.demo.model.Faculty;
import com.example.demo.repository.FacultyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

@Configuration
@RequiredArgsConstructor
public class ServiceConfig {

    private final FacultyRepository facultyRepo;

    @PostConstruct
    private void populateDB() {

        facultyRepo.save(Faculty.builder().name("Information").build());
        facultyRepo.save(Faculty.builder().name("Electronics").build());
        facultyRepo.save(Faculty.builder().name("Design").build());
        facultyRepo.save(Faculty.builder().name("Economics").build());
        facultyRepo.save(Faculty.builder().name("Civil and environmental engineering").build());
    }
}
