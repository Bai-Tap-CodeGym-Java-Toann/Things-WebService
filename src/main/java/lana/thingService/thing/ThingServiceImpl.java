package lana.thingService.thing;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service("thingService")
public class ThingServiceImpl implements ThingService {
    private final ThingRepo thingRepo;

    @Autowired
    public ThingServiceImpl(ThingRepo thingRepo) {
        this.thingRepo = thingRepo;
    }


    @Override
    public Page<Thing> findAll(Pageable pageable) {
        return thingRepo.findAll(pageable);
    }

    @Override
    public void delete(Integer id) {
        thingRepo.deleteById(id);
    }

    @Override
    public Thing create(Thing thing) {
        return thingRepo.save(thing);
    }

    @Override
    public Optional<Thing> find(Integer id) {
        return thingRepo.findById(id);
    }

    @Override
    public Optional<Thing> update(Thing thing) {
        Optional<Thing> existed = thingRepo.findById(thing.getId());
        if (existed.isPresent()) {
            existed = Optional.of(thingRepo.save(thing));
        }
        return existed;
    }
}
