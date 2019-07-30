package lana.thingService.thing;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.lang.reflect.Method;
import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping(path = "/api/things", produces = MediaType.APPLICATION_JSON_VALUE)
public class ThingController {
    private ThingService thingService;

    @Autowired
    public void setThingService(ThingService thingService) {
        this.thingService = thingService;
    }

    //====================================
    @GetMapping
    public ResponseEntity<List<Thing>> getAllThing() {
        List<Thing> things = thingService.findAll();
        if (things.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(things, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Thing> getOneThing(@PathVariable int id) {
        Thing foundedThing = thingService.findOne(id);
        if (foundedThing != null) {
            return new ResponseEntity<>(foundedThing, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping
    public ResponseEntity<Thing> createThing(@RequestBody Thing newThing,
                                             UriComponentsBuilder uriComponentsBuilder) {
        thingService.save(newThing);
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(uriComponentsBuilder.path("/things/" + newThing.getId()).build().toUri());
        return new ResponseEntity<>(newThing, headers, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Thing> updateThing(@RequestBody Thing updatedThing,
                                             @PathVariable int id) {
        if (thingService.findOne(id) == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        updatedThing.setId(id);
        thingService.save(updatedThing);
        return new ResponseEntity<>(updatedThing, HttpStatus.OK);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Thing> patchThing(@PathVariable int id,
                                            @RequestBody Thing patchThing) {
        Thing thing = thingService.findOne(id);
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
        thingService.save(thing);
        return new ResponseEntity<>(thing, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteThing(@PathVariable int id) {
        if (thingService.findOne(id) == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        thingService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
