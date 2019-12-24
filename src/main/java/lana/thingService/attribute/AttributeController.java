package lana.thingService.attribute;

import lana.thingService.thing.Thing;
import lana.thingService.thing.ThingRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping(path = "/api/attributes", produces = MediaType.APPLICATION_JSON_VALUE)
public class AttributeController {
    private final AttributeService attributeService;
    private final ThingRepo thingRepo;

    @Autowired
    public AttributeController(AttributeService attributeService, ThingRepo thingRepo) {
        this.attributeService = attributeService;
        this.thingRepo = thingRepo;
    }

    //=============================================
    @GetMapping("/{id}/things")
    public ResponseEntity<Page<Thing>> getAllAttributeThings(@PathVariable int id, Pageable pageable) {
        if (attributeService.find(id).isPresent()) {
            Page<Thing> things = thingRepo.findAllByAttribute_Id(id, pageable);
            return ResponseEntity.ok(things);
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping
    public ResponseEntity<Page<Attribute>> getAllAttribute(Pageable pageable) {
        Page<Attribute> attributes = attributeService.findAll(pageable);
        if (attributes.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(attributes);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Attribute> getOneAttribute(@PathVariable int id) {
        Optional<Attribute> found = attributeService.find(id);
        if (found.isPresent()) {
            return ResponseEntity.ok(found.get());
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<Attribute> createAttribute(@RequestBody Attribute attribute,
                                                     UriComponentsBuilder uriComponentsBuilder) {
        if (attributeService.find(attribute.getId()).isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
        Attribute saved = attributeService.create(attribute);
        URI uri = uriComponentsBuilder.path("/attributes/" + saved.getId()).build().toUri();
        return ResponseEntity.created(uri).body(saved);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Attribute> updateAttribute(@RequestBody Attribute attribute,
                                                     @PathVariable int id) {
        attribute.setId(id);
        Optional<Attribute> updated = attributeService.update(attribute);
        if (updated.isPresent()) {
            return ResponseEntity.ok(updated.get());
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Attribute> deleteAttribute(@PathVariable int id) {
        attributeService.delete(id);
        return ResponseEntity.ok().build();
    }
}
