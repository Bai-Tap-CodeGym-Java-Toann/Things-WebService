package lana.thingService.attribute

import lana.thingService.thing.Thing
import lana.thingService.thing.ThingRepo
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.util.UriComponentsBuilder

@RestController
@CrossOrigin(origins = ["http://localhost:4200"])
@RequestMapping(path = ["/api/attributes"], produces = [MediaType.APPLICATION_JSON_VALUE])
class AttributeController
@Autowired
constructor(private val attributeService: AttributeService, private val thingRepo: ThingRepo) {

    @GetMapping("/{id}/things")
    fun getAllAttributeThings(@PathVariable id: Int, pageable: Pageable): ResponseEntity<Page<Thing>> {
        return if (attributeService.find(id) != null) {
            val things = thingRepo.findAllByAttributeId(id, pageable)
            ResponseEntity.ok(things)
        } else {
            ResponseEntity.notFound().build()
        }
    }

    @GetMapping
    fun getAllAttribute(pageable: Pageable): ResponseEntity<Page<Attribute>> {
        val attributes = attributeService.findAll(pageable)
        return if (attributes.isEmpty) ResponseEntity.noContent().build() else ResponseEntity.ok(attributes)
    }

    @GetMapping("/{id}")
    fun getOneAttribute(@PathVariable id: Int): ResponseEntity<Attribute> {
        val found = attributeService.find(id)
        return if (found != null) ResponseEntity.ok(found) else ResponseEntity.notFound().build()
    }

    @PostMapping
    fun createAttribute(@RequestBody attribute: Attribute,
                        uriComponentsBuilder: UriComponentsBuilder): ResponseEntity<Attribute> {
        val saved = attributeService.create(attribute)
        return if (saved != null) {
            val uri = uriComponentsBuilder.path("/attributes/" + saved.id).build().toUri()
            ResponseEntity.created(uri).body(saved)
        } else {
            ResponseEntity.status(HttpStatus.CONFLICT).build()
        }
    }

    @PutMapping("/{id}")
    fun updateAttribute(@RequestBody attribute: Attribute,
                        @PathVariable id: Int): ResponseEntity<Attribute> {
        attribute.id = id
        val updated = attributeService.update(attribute)
        return if (updated != null) ResponseEntity.ok(updated) else ResponseEntity.notFound().build()
    }

    @DeleteMapping("/{id}")
    fun deleteAttribute(@PathVariable id: Int): ResponseEntity<Attribute> {
        attributeService.delete(id)
        return ResponseEntity.ok().build()
    }
}
