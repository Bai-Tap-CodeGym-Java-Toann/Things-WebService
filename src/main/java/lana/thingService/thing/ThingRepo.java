package lana.thingService.thing;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface ThingRepo extends PagingAndSortingRepository<Thing, Integer> {
    @Override
    Page<Thing> findAll(Pageable pageable);

    List<Thing> findAllByAttribute_Id(int id);
}
