package lana.thingService.attribute;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;


public interface AttributeService {

    Optional<Attribute> find(Integer id);

    Optional<Attribute> update(Attribute attribute);

    Attribute create(Attribute attribute);

    void delete(Integer id);

    Page<Attribute> findAll(Pageable pageable);
}
