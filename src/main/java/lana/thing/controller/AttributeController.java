package lana.thing.controller;

import lana.thing.model.Attribute;
import lana.thing.service.AttributeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping(path = "/api", produces = MediaType.APPLICATION_JSON_VALUE)
public class AttributeController {
    private AttributeService attributeService;

    @Autowired
    public void setUpAttributeController(AttributeService attributeService) {
        this.attributeService = attributeService;
    }

    @GetMapping("/attributes")
    public ResponseEntity<List<Attribute>> getAllAttribute() {
        List<Attribute> attributes = attributeService.findAll();
        if (attributes.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(attributes, HttpStatus.OK);
    }

    @PostMapping("/attributes")
    public ResponseEntity<Attribute> createAttribute(@RequestBody Attribute newAttribute,
                                                     UriComponentsBuilder uriComponentsBuilder) {
        attributeService.save(newAttribute);
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(uriComponentsBuilder.path("/attributes/" + newAttribute.getId()).build().toUri());
        return new ResponseEntity<>(newAttribute, headers, HttpStatus.CREATED);

    }
}
