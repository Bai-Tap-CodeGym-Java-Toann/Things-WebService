package lana.attributeService.attribute;

import lana.thingService.attribute.Attribute;
import lana.thingService.attribute.AttributeExistedException;
import lana.thingService.attribute.AttributeNotFoundException;
import lana.thingService.attribute.AttributeService;
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
        try {
            attributeService.find(id);
            Page<Thing> things = thingRepo.findAllByAttribute_Id(id, pageable);
            return ResponseEntity.ok(things);
        } catch (AttributeNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
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
        try {
            Attribute found = attributeService.find(id);
            return ResponseEntity.ok(found);
        } catch (AttributeNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<Attribute> createAttribute(@RequestBody Attribute attribute,
                                                     UriComponentsBuilder uriComponentsBuilder) {
        try {
            Attribute saved = attributeService.create(attribute);
            URI uri = uriComponentsBuilder.path("/attributes/" + saved.getId()).build().toUri();
            return ResponseEntity.created(uri).body(saved);
        } catch (AttributeExistedException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Attribute> updateAttribute(@RequestBody Attribute attribute,
                                                     @PathVariable int id) {
        try {
            attribute.setId(id);
            Attribute updated = attributeService.update(attribute);
            return ResponseEntity.ok(updated);
        } catch (AttributeNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping
    public ResponseEntity<Attribute> deleteAttribute(@PathVariable int id) {
        attributeService.delete(id);
        return ResponseEntity.ok().build();
    }
}
