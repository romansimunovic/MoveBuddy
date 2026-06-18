package com.movebuddy.backend.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class DatabaseInitializer implements CommandLineRunner {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public void run(String... args) throws Exception {
        // Trajno i sigurno uklanjanje starog check constrainta koji je blokirao tekstualni unos aktivnosti
        jdbcTemplate.execute("ALTER TABLE activities DROP CONSTRAINT IF EXISTS activities_activity_type_check");
    }
}