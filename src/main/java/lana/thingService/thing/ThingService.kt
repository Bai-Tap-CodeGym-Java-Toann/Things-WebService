package lana.thingService.thing

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

interface ThingService {

    fun update(thing: Thing): Thing?

    fun create(thing: Thing): Thing?

    fun find(id: Int): Thing?

    fun delete(id: Int)

    fun findAll(pageable: Pageable): Page<Thing>
}
