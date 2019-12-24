package lana.thingService.thing;

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
@RequestMapping(path = "/api/things", produces = MediaType.APPLICATION_JSON_VALUE)
public class ThingController {
    private final ThingService thingService;

    @Autowired
    public ThingController(ThingService thingService) {
        this.thingService = thingService;
    }

    //====================================
    @GetMapping
    public ResponseEntity<Page<Thing>> getAllThing(Pageable pageable) {
        Page<Thing> things = thingService.findAll(pageable);
        return things.isEmpty()
                ? ResponseEntity.noContent().build()
                : ResponseEntity.ok(things);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Thing> getOneThing(@PathVariable int id) {
        Optional<Thing> found = thingService.find(id);
        return found
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Thing> createThing(@RequestBody Thing thing,
                                             UriComponentsBuilder uriComponentsBuilder) {
        if (thingService.find(thing.getId()).isPresent()) {
            Thing saved = thingService.create(thing);
            URI uri = uriComponentsBuilder.path("/things/" + saved.getId()).build().toUri();
            return ResponseEntity.created(uri).body(saved);
        }
        return ResponseEntity.status(HttpStatus.CONFLICT).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Thing> updateThing(@RequestBody Thing thing,
                                             @PathVariable int id) {
        thing.setId(id);
        Optional<Thing> updated = thingService.update(thing);
        return updated
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Thing> deleteThing(@PathVariable int id) {
        thingService.delete(id);
        return ResponseEntity.ok().build();
    }
}
