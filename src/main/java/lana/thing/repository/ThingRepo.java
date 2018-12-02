package lana.thing.repository;

import lana.thing.model.Thing;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ThingRepo extends CrudRepository<Thing, Integer> {
    @Override
    List<Thing> findAll();

    List<Thing> findAllByMainAttribute_Id(int id);
}
