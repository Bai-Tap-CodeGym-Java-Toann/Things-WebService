package lana.thingService.attribute;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface AttributeRepo extends CrudRepository<Attribute, Integer> {
    @Override
    List<Attribute> findAll();
}
