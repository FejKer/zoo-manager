package me.omigo.zoomanager;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Optional;
import me.omigo.zoomanager.entities.*;
import me.omigo.zoomanager.repositories.AnimalRepository;
import me.omigo.zoomanager.repositories.ZoneRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest(
    webEnvironment = SpringBootTest.WebEnvironment.MOCK,
    classes = ZooManagerApplication.class)
@AutoConfigureMockMvc
class ZooManagerApplicationTests {
  @Autowired private MockMvc mvc;

  @Autowired private AnimalRepository animalRepository;
  @Autowired private ZoneRepository zoneRepository;

  @Test
  void initDatabase() {
    Zone z1 = new Zone("z1");
    Zone z2 = new Zone("z2");
    zoneRepository.save(z1);
    zoneRepository.save(z2);
    animalRepository.save(new Lion("Bilbo", z1));
    animalRepository.save(new Rabbit("Frodo", z1));
    animalRepository.save(new Rabbit("Rabbit", z1));
    animalRepository.save(new Elephant("Elephant", z2)); // preloading entities
    animalRepository.save(new Elephant("Elephant", z2));
  }

  @Test
  void contextLoads() {}

  @Test
  void givenAnimals_whenGetAnimals_thenStatusOk() throws Exception {
    mvc.perform(get("/animals").contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(content().contentTypeCompatibleWith(new MediaType("application", "*+json")));
  }

  @Test
  void givenAnimals_whenGetAnimals_thenFirstAnimalNameIsBilbo() throws Exception {
    Optional<Animal> testAnimal = animalRepository.findById(1L);
    assertThat(testAnimal.get().getName())
        .isEqualTo("Bilbo"); // testing if preloaded animals are correct
  }

  @Test
  void givenAnimals_whenCreateAnimals_thenStatusOk() throws Exception {
    mvc.perform(
            post("/animals")
                .contentType(MediaType.APPLICATION_JSON)
                .content(
                    """
                                {
                                                "name": "TestElephant",
                                                "species": "Elephant",
                                                "zone": "z1"
                                }"""))
        .andExpect(status().isOk())
        .andReturn(); // test if creating animal works
  }

  @Test
  void givenAnimals_whenCreateZoneAndGetLeastPopulatedZone_thenReturnedZoneIsZ3() throws Exception {
    mvc.perform(
            post("/zones")
                .contentType(MediaType.APPLICATION_JSON)
                .content(
                    """
                                {
                                                "name": "z3"
                                }"""))
        .andExpect(status().isOk())
        .andReturn();
    String s =
        mvc.perform(get("/zones/least-populated").contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().contentTypeCompatibleWith(new MediaType("application", "*+json")))
            .andReturn()
            .getResponse()
            .getContentAsString();
    assertThat(s.contains("\"name\":\"z3\""))
        .isTrue(); // testing if least populated done is newly created z3
  }
}
