package lana.thingService.thing

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
@RequestMapping(path = ["/api/things"], produces = [MediaType.APPLICATION_JSON_VALUE])
class ThingController
@Autowired
constructor(private val thingService: ThingService) {

    @GetMapping
    fun getAllThing(pageable: Pageable): ResponseEntity<Page<Thing>> {
        val things = thingService.findAll(pageable)
        return if (things.isEmpty) ResponseEntity.noContent().build() else ResponseEntity.ok(things)
    }

    @GetMapping("/{id}")
    fun getOneThing(@PathVariable id: Int): ResponseEntity<Thing> {
        val found = thingService.find(id)
        return if (found != null) ResponseEntity.ok(found) else ResponseEntity.notFound().build()
    }

    @PostMapping
    fun createThing(@RequestBody thing: Thing,
                    uriComponentsBuilder: UriComponentsBuilder): ResponseEntity<Thing> {
        val saved = thingService.create(thing)
        return if (saved != null) {
            val uri = uriComponentsBuilder.path("/things/" + saved.id).build().toUri()
            ResponseEntity.created(uri).body(saved)
        } else {
            ResponseEntity.status(HttpStatus.CONFLICT).build()
        }

    }

    @PutMapping("/{id}")
    fun updateThing(@RequestBody thing: Thing,
                    @PathVariable id: Int): ResponseEntity<Thing> {
        thing.id = id
        val updated = thingService.update(thing)
        return if (updated != null) ResponseEntity.ok(updated) else ResponseEntity.notFound().build()
    }

    @DeleteMapping("/{id}")
    fun deleteThing(@PathVariable id: Int): ResponseEntity<Thing> {
        thingService.delete(id)
        return ResponseEntity.ok().build()
    }
}
