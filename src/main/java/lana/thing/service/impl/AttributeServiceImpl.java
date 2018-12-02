package lana.thing.service.impl;

import lana.thing.model.Attribute;
import lana.thing.repository.AttributeRepo;
import lana.thing.service.AttributeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("attributeService")
public class AttributeServiceImpl implements AttributeService {
    private AttributeRepo attributeRepo;

    @Autowired
    public void setAttributeRepo(AttributeRepo attributeRepo) {
        this.attributeRepo = attributeRepo;
    }

    @Override
    public List<Attribute> findAll() {
        return attributeRepo.findAll();
    }

    @Override
    public Attribute findOne(int id) {
        return attributeRepo.findById(id).orElse(null);
    }

    @Override
    public void save(Attribute attribute) {
        attributeRepo.save(attribute);
    }

    @Override
    public boolean delete(int id) {
        if (findOne(id) != null) {
            attributeRepo.deleteById(id);
            return true;
        }
        return false;
    }
}
