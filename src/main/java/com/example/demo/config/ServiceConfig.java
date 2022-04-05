package com.example.demo.config;


import com.example.demo.model.Faculty;
import com.example.demo.repository.FacultyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import javax.annotation.PostConstruct;
import java.util.Locale;

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

    /// Default localization and language (for validation message)
    @Bean
    public LocaleResolver localeResolver() {
        SessionLocaleResolver slr = new SessionLocaleResolver();
        slr.setDefaultLocale(Locale.ENGLISH);
        return slr;
    }
}
