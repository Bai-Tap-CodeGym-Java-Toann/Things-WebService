package lana.thing.service;

import lana.thing.model.Thing;

import java.util.List;

public interface ThingService {
    List<Thing> findAll();

    List<Thing> findByAttribute(int attributeId);

    Thing findOne(int thingId);

    void save(Thing thing);

    boolean delete(int thingId);
}
