package com.example.demo.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedMethods(
                        HttpMethod.HEAD.toString(),
                        HttpMethod.GET.toString(),
                        HttpMethod.PUT.toString(),
                        HttpMethod.POST.toString(),
                        HttpMethod.DELETE.toString(),
                        HttpMethod.PATCH.toString());
    }
}