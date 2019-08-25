package lana.thingService.attribute;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface AttributeService {
    Page<Attribute> findAll(Pageable pageable);

    Attribute find(Integer id) throws AttributeNotFoundException;

    Attribute update(Attribute attribute) throws AttributeNotFoundException;

    Attribute create(Attribute attribute) throws AttributeExistedException;

    void delete(Integer id);
}
