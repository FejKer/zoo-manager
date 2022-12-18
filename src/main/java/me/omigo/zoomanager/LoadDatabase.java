package me.omigo.zoomanager;

import me.omigo.zoomanager.entities.Elephant;
import me.omigo.zoomanager.entities.Lion;
import me.omigo.zoomanager.entities.Rabbit;
import me.omigo.zoomanager.entities.Zone;
import me.omigo.zoomanager.repositories.AnimalRepository;
import me.omigo.zoomanager.repositories.ZoneRepository;
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
      log.info("Preloading " + animalRepository.save(new Rabbit("Rabbit", testZone)));
      log.info(
          "Preloading "
              + animalRepository.save(
                  new Elephant("Elephant", testZone))); // testing pre-loaded entities
    };
  }
}
