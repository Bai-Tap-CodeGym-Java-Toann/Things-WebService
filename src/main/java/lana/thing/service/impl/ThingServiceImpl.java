package lana.thing.service.impl;

import lana.thing.model.Thing;
import lana.thing.repository.ThingRepo;
import lana.thing.service.ThingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("thingService")
public class ThingServiceImpl implements ThingService {
    private ThingRepo thingRepo;

    @Autowired
    public void setThingRepo(ThingRepo thingRepo) {
        this.thingRepo = thingRepo;
    }

    //==================finding======================
    @Override
    public List<Thing> findAll() {
        return thingRepo.findAll();
    }

    @Override
    public List<Thing> findByAttribute(int attributeId) {
        return thingRepo.findAllByAttribute_Id(attributeId);
    }

    @Override
    public Thing findOne(int thingId) {
        return thingRepo.findById(thingId).orElse(null);
    }

    //=========================================
    @Override
    public void save(Thing thing) {
        thingRepo.save(thing);
    }

    @Override
    public boolean delete(int thingId) {
        if (findOne(thingId) != null) {
            thingRepo.deleteById(thingId);
            return true;
        }
        return false;
    }
}
