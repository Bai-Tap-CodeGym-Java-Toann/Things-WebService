package lana.thingService.attribute

import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.data.repository.PagingAndSortingRepository

interface AttributeRepo : PagingAndSortingRepository<Attribute, Int>, JpaSpecificationExecutor<Attribute>
