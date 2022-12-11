package me.omigo.zoomanager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class LoadDatabase {

    private static final Logger log = LoggerFactory.getLogger(LoadDatabase.class);

    @Bean
    CommandLineRunner initDatabase(AnimalRepository animalRepository, ZoneRepository zoneRepository) {

        return args -> {
            Zone testZone = new Zone("z1");
            zoneRepository.save(testZone);
            log.info("Preloading " + animalRepository.save(new Lion("Bilbo", testZone)));
            log.info("Preloading " + animalRepository.save(new Lion("Frodo", testZone)));
            log.info(animalRepository.findAll().toString());                                      //this is where I try to log content to console
        };
    }
}