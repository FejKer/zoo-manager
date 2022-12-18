package me.omigo.zoomanager.repositories;

import me.omigo.zoomanager.entities.Zone;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ZoneRepository extends JpaRepository<Zone, Long> {
    List<Zone> findByName(String name);
}
