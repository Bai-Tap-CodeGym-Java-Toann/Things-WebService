package lana.thingService.attribute;

import lana.thingService.thing.Thing;
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
@RequestMapping(path = "/api/attributes", produces = MediaType.APPLICATION_JSON_VALUE)
public class AttributeController {
    private AttributeService attributeService;

    @Autowired
    public void setUpAttributeController(AttributeService attributeService) {
        this.attributeService = attributeService;
    }

    //=============================================
    @GetMapping("/{id}/things")
    public ResponseEntity<List<Thing>> getAllAttributeThings(@PathVariable int id) {
        Attribute attribute = attributeService.findOne(id);
        if (attribute != null) {
            List<Thing> things = attribute.getThings();
            if (things.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(things, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping
    public ResponseEntity<List<Attribute>> getAllAttribute() {
        List<Attribute> attributes = attributeService.findAll();
        if (attributes.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(attributes, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Attribute> getOneAttribute(@PathVariable int id) {
        Attribute attribute = attributeService.findOne(id);
        if (attribute != null) {
            return new ResponseEntity<>(attribute, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping
    public ResponseEntity<Attribute> createAttribute(@RequestBody Attribute newAttribute,
                                                     UriComponentsBuilder uriComponentsBuilder) {
        attributeService.save(newAttribute);
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(uriComponentsBuilder.path("/attributes/" + newAttribute.getId()).build().toUri());
        return new ResponseEntity<>(newAttribute, headers, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAttribute(@PathVariable int id) {
        if (attributeService.findOne(id) != null) {
            attributeService.delete(id);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Attribute> updateAttribute(@PathVariable int id,
                                                     @RequestBody Attribute attribute) {
        if (attributeService.findOne(id) != null) {
            attribute.setId(id);
            attributeService.save(attribute);
            return new ResponseEntity<>(attribute, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PatchMapping("/{id}")
    private ResponseEntity<Attribute> pacthAttribute(@PathVariable int id,
                                                     @RequestBody Attribute patch) {
        Attribute attribute = attributeService.findOne(id);
        if (attribute != null) {
            for (Method method : Attribute.class.getDeclaredMethods()) {
                if (method.getName().matches("^(get).+$")) {
                    try {
                        Object returnedObject = method.invoke(patch);
                        if (returnedObject != null) {
                            String fieldName = method.getName().substring(3);
                            Class returnedType = method.getReturnType();
                            Method setter = Attribute.class.getDeclaredMethod("set" + fieldName, returnedType);
                            setter.invoke(attribute, returnedType.cast(returnedObject));
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
                    }
                }
            }
            attributeService.save(attribute);
            return new ResponseEntity<>(attribute, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
