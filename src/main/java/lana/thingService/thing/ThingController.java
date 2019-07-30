package lana.thingService.thing;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.lang.reflect.Method;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping(path = "/api/things", produces = MediaType.APPLICATION_JSON_VALUE)
public class ThingController {
    private ThingRepo thingRepo;

    @Autowired
    public void setThingRepo(ThingRepo thingRepo) {
        this.thingRepo = thingRepo;
    }

    //====================================
    @GetMapping
    public ResponseEntity<Page<Thing>> getAllThing(Pageable pageable) {
        Page<Thing> things = thingRepo.findAll(pageable);
        if (things.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(things, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Thing> getOneThing(@PathVariable int id) {
        Thing foundedThing = thingRepo.findById(id).orElse(null);
        if (foundedThing != null) {
            return new ResponseEntity<>(foundedThing, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping
    public ResponseEntity<Thing> createThing(@RequestBody Thing newThing,
                                             UriComponentsBuilder uriComponentsBuilder) {
        thingRepo.save(newThing);
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(uriComponentsBuilder.path("/things/" + newThing.getId()).build().toUri());
        return new ResponseEntity<>(newThing, headers, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Thing> updateThing(@RequestBody Thing updatedThing,
                                             @PathVariable int id) {
        if (thingRepo.findById(id).orElse(null) == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        updatedThing.setId(id);
        thingRepo.save(updatedThing);
        return new ResponseEntity<>(updatedThing, HttpStatus.OK);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Thing> patchThing(@PathVariable int id,
                                            @RequestBody Thing patchThing) {
        Thing thing = thingRepo.findById(id).orElse(null);
        if (thing == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
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
                    return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
                }
            }
        }
        thingRepo.save(thing);
        return new ResponseEntity<>(thing, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteThing(@PathVariable int id) {
        if (thingRepo.findById(id).orElse(null) == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        thingRepo.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
