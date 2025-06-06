package com.project.ToDoList.infra.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins(
                        "http://localhost:5173",             // React Vite local
                        "http://localhost:8081",             // Metro bundler web
                        "http://192.168.1.128:8081",         // React Native via Expo Go
                        "http://192.168.1.128:19000",        // Expo Dev Tools
                        "http://192.168.1.128:19006"         // Expo Web Preview
                )
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                .allowedHeaders("*")
                .allowCredentials(true);
    }
}