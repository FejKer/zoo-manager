package me.omigo.zoomanager;

import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
public class ZoneController {
    private final ZoneRepository zoneRepository;
    private final ZoneModelAssembler zoneModelAssembler;

    public ZoneController(ZoneRepository zoneRepository, ZoneModelAssembler zoneModelAssembler) {
        this.zoneRepository = zoneRepository;
        this.zoneModelAssembler = zoneModelAssembler;
    }
    @GetMapping("/zones")
    public CollectionModel<EntityModel<Zone>> getAllZones() {
        List<EntityModel<Zone>> zones = zoneRepository.findAll().stream() //
                .map(zoneModelAssembler::toModel) //
                .collect(Collectors.toList());

        return CollectionModel.of(zones, linkTo(methodOn(ZoneController.class).getAllZones()).withSelfRel());
    }

    @GetMapping("/zones/id/{id}")
    public EntityModel<Zone> getOneZone(@PathVariable Long id) {
        Zone zone = zoneRepository.findById(id).orElseThrow(() -> new ZoneNotFoundException(id));

        return zoneModelAssembler.toModel(zone);
    }

    @PostMapping("/zones")
    public Zone createZone(@RequestBody Zone zone) {
        if (zoneRepository.findByName(zone.getName()).size() != 0) {
            throw new IllegalArgumentException("Zone exists with name " + zone.getName());
        } else {
            return zoneRepository.save(zone);
        }
    }

}