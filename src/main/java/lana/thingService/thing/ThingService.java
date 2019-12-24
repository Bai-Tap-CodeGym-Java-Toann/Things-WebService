package lana.thingService.thing;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface ThingService {

    Optional<Thing> update(Thing thing);

    Optional<Thing> find(Integer id);

    Thing create(Thing thing);

    void delete(Integer id);

    Page<Thing> findAll(Pageable pageable);
}
