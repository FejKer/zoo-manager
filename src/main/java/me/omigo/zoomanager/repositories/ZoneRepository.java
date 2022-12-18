package me.omigo.zoomanager.repositories;

import java.util.List;
import me.omigo.zoomanager.entities.Zone;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ZoneRepository extends JpaRepository<Zone, Long> {
  List<Zone> findByName(String name);
}
