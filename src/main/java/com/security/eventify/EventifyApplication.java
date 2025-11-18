package com.security.eventify;

import io.github.cdimascio.dotenv.Dotenv;
import jakarta.annotation.PostConstruct;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Objects;

@SpringBootApplication
public class EventifyApplication {

    public static void main(String[] args) {
        SpringApplication.run(EventifyApplication.class, args);
    }

    @PostConstruct
    public void loadEnv() {
        Dotenv dotenv = Dotenv.configure().load();

        System.setProperty("DB_HOST", Objects.requireNonNull(dotenv.get("DB_HOST")));
        System.setProperty("DB_PORT", Objects.requireNonNull(dotenv.get("DB_PORT")));
        System.setProperty("DB_NAME", Objects.requireNonNull(dotenv.get("DB_NAME")));
        System.setProperty("DB_USER", Objects.requireNonNull(dotenv.get("DB_USER")));
        System.setProperty("DB_PASSWORD", Objects.requireNonNull(dotenv.get("DB_PASSWORD")));
    }
}
