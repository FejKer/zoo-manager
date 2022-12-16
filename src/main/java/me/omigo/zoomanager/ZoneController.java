package me.omigo.zoomanager;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

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

    @GetMapping("/zones/{id}")
    public EntityModel<Zone> getOneZone(@PathVariable Long id) {
        Zone zone = zoneRepository.findById(id).orElseThrow(() -> new ZoneNotFoundException(id));

        return zoneModelAssembler.toModel(zone);
    }

}
