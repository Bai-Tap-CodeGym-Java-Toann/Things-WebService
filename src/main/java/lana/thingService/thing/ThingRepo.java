package lana.thingService.thing;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ThingRepo extends CrudRepository<Thing, Integer> {
    @Override
    List<Thing> findAll();

    List<Thing> findAllByAttribute_Id(int id);
}
