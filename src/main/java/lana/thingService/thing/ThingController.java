package lana.thingService.thing;

import lana.thingService.util.objectmapper.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.lang.reflect.Method;
import java.net.URI;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping(path = "/api/things", produces = MediaType.APPLICATION_JSON_VALUE)
public class ThingController {
    private final ObjectMapper objectMapper;
    private final ThingRepo thingRepo;

    @Autowired
    public ThingController(ThingRepo thingRepo, ObjectMapper objectMapper) {
        this.thingRepo = thingRepo;
        this.objectMapper = objectMapper;
    }

    //====================================
    @GetMapping
    public ResponseEntity<Page<Thing>> getAllThing(Pageable pageable) {
        Page<Thing> things = thingRepo.findAll(pageable);
        if (things.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(things);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Thing> getOneThing(@PathVariable int id) {
        Thing foundedThing = thingRepo.findById(id).orElse(null);
        if (foundedThing != null) {
            return ResponseEntity.ok(foundedThing);
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<Thing> createThing(@RequestBody Thing thing,
                                             UriComponentsBuilder uriComponentsBuilder) {
        Thing saved = thingRepo.save(thing);
        URI uri = uriComponentsBuilder.path("/things/" + saved.getId()).build().toUri();
        return ResponseEntity.created(uri).body(saved);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Thing> updateThing(@RequestBody Thing thing,
                                             @PathVariable int id) {
        if (thingRepo.findById(id).orElse(null) == null) {
            return ResponseEntity.notFound().build();
        }
        thing.setId(id);
        Thing updatedThing = thingRepo.save(thing);
        return ResponseEntity.ok(updatedThing);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Thing> patchThing(@PathVariable int id,
                                            @RequestBody Thing patchThing) {
        Thing thing = thingRepo.findById(id).orElse(null);
        if (thing == null) {
            return ResponseEntity.notFound().build();
        }
        // iterate through all methods of Thing.class
        for (Method method : Thing.class.getDeclaredMethods()) {
            //findGetter
            if (method.getName().matches("^(get).+$")) {
                try {
                    //invoke getter form pathThing
                    Object returnedObject = method.invoke(patchThing);
                    if (returnedObject != null) {
                        String fieldName = method.getName().substring(3);
                        Class returnedClass = method.getReturnType();
                        Method setter = Thing.class.getDeclaredMethod("set" + fieldName, returnedClass);
                        //invoke setter form thing
                        setter.invoke(thing, returnedClass.cast(returnedObject));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    return ResponseEntity.badRequest().build();
                }
            }
        }
        Thing saved = thingRepo.save(thing);
        return ResponseEntity.ok(saved);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteThing(@PathVariable int id) {
        if (thingRepo.findById(id).orElse(null) == null) {
            return ResponseEntity.notFound().build();
        }
        thingRepo.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
