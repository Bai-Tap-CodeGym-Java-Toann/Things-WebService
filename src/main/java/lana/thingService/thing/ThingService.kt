package lana.thingService.thing

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.domain.Specification

interface ThingService {

    fun update(thing: Thing): Thing?

    fun create(thing: Thing): Thing?

    fun find(id: Int): Thing?

    fun delete(id: Int)

    fun findAll(pageable: Pageable): Page<Thing>

    fun findAll(specification: Specification<Thing>, pageable: Pageable): Page<Thing>

}
