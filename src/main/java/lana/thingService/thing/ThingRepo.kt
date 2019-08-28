package lana.thingService.thing

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.data.repository.PagingAndSortingRepository

interface ThingRepo : PagingAndSortingRepository<Thing, Int>, JpaSpecificationExecutor<Thing> {

    fun findAllByAttributeId(id: Int?, pageable: Pageable): Page<Thing>
}
