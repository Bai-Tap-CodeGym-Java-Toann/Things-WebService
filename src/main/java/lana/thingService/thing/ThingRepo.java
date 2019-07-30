package lana.thingService.thing;

import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface ThingRepo extends PagingAndSortingRepository<Thing, Integer> {
    List<Thing> findAllByAttribute_Id(int id);
}
