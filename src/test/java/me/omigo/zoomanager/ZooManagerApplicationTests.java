package me.omigo.zoomanager;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Optional;
import me.omigo.zoomanager.entities.Animal;
import me.omigo.zoomanager.repositories.AnimalRepository;
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

  @Autowired private AnimalRepository repository;

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
    Optional<Animal> testAnimal = repository.findById(1L);
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
        .andExpect(
            content()
                .string(
                    "{\"id\":5,\"name\":\"TestElephant\",\"species\":\"Elephant\",\"requiredFood\":20,\"zoneName\":\"z1\"}"))
        .andReturn(); // test if creating animal returns animal body
  }
}
