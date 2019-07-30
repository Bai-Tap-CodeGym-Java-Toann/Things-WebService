package lana.thingService.thing;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ThingService {
    Thing update(Thing thing) throws ThingNotFoundException;

    Thing create(Thing thing) throws ThingExistedException;

    Thing find(Integer id) throws ThingNotFoundException;

    void delete(Integer id);

    Page<Thing> findAll(Pageable pageable);
}
