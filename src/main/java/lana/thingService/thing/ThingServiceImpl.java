package lana.thingService.thing;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

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
    public Thing create(Thing thing) throws ThingExistedException {
        if (thingRepo.existsById(thing.getId())) throw new ThingExistedException();
        return thingRepo.save(thing);
    }

    @Override
    public Thing find(Integer id) throws ThingNotFoundException {
        return thingRepo.findById(id).orElseThrow(ThingNotFoundException::new);
    }


    @Override
    public Thing update(Thing thing) throws ThingNotFoundException {
        thing = thingRepo.findById(thing.getId()).orElseThrow(ThingNotFoundException::new);
        return thingRepo.save(thing);
    }
}
