package lana.thingService.thing;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;


public interface ThingRepo extends PagingAndSortingRepository<Thing, Integer> {
    Page<Thing> findAllByAttribute_Id(Integer id, Pageable pageable);
}
