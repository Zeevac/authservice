package com.safayildirim.authservice;

import com.safayildirim.authservice.models.Permission;
import com.safayildirim.authservice.repos.PermissionsRepository;
import com.safayildirim.authservice.repos.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class LoadDatabase {

    private static final Logger log = LoggerFactory.getLogger(LoadDatabase.class);

    @Bean
    CommandLineRunner initDatabase(UserRepository userRepository, PermissionsRepository permissionsRepository) {

        return args -> {
            log.info("Preloading " + permissionsRepository.save(new Permission("calculate-sum", 1)));
        };
    }
}