package lana.thingService.thing;

import lana.thingService.util.objectmapper.ObjectMapper;
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
@RequestMapping(path = "/api/things", produces = MediaType.APPLICATION_JSON_VALUE)
public class ThingController {
    private final ObjectMapper objectMapper;
    private final ThingService thingService;

    @Autowired
    public ThingController(ThingService thingService, ObjectMapper objectMapper) {
        this.thingService = thingService;
        this.objectMapper = objectMapper;
    }

    //====================================
    @GetMapping
    public ResponseEntity<Page<Thing>> getAllThing(Pageable pageable) {
        Page<Thing> things = thingService.findAll(pageable);
        if (things.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(things);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Thing> getOneThing(@PathVariable int id) {
        try {
            Thing found = thingService.find(id);
            return ResponseEntity.ok(found);
        } catch (ThingNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<Thing> createThing(@RequestBody Thing thing,
                                             UriComponentsBuilder uriComponentsBuilder) {
        try {
            Thing saved = thingService.create(thing);
            URI uri = uriComponentsBuilder.path("/things/" + saved.getId()).build().toUri();
            return ResponseEntity.created(uri).body(saved);
        } catch (ThingExistedException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Thing> updateThing(@RequestBody Thing thing,
                                             @PathVariable int id) {
        try {
            thing.setId(id);
            Thing updated = thingService.update(thing);
            return ResponseEntity.ok(updated);
        } catch (ThingNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
