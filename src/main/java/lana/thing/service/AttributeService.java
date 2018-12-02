package lana.thing.service;

import lana.thing.model.Attribute;

import java.util.List;

public interface AttributeService {
    List<Attribute> findAll();

    Attribute findOne(int id);

    void save(Attribute attribute);

    boolean delete(int id);
}
