package lana.thing.repository;

import lana.thing.model.Attribute;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface AttributeRepo extends CrudRepository<Attribute,Integer> {
    @Override
    List<Attribute> findAll();
}
