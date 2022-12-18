package me.omigo.zoomanager.repositories;

import java.util.List;
import me.omigo.zoomanager.entities.Animal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AnimalRepository extends JpaRepository<Animal, Long> {
  List<Animal> findByName(String name);
}
