package lana.thingService.thing

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.repository.PagingAndSortingRepository

interface ThingRepo : PagingAndSortingRepository<Thing, Int> {

    fun findAllByAttributeId(id: Int?, pageable: Pageable): Page<Thing>
}
