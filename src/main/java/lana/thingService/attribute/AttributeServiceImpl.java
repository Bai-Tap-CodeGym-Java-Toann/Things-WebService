package lana.thingService.attribute;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service("attributeService")
public class AttributeServiceImpl implements AttributeService {
    private final AttributeRepo attributeRepo;

    @Autowired
    public AttributeServiceImpl(AttributeRepo attributeRepo) {
        this.attributeRepo = attributeRepo;
    }

    @Override
    public Page<Attribute> findAll(Pageable pageable) {
        return attributeRepo.findAll(pageable);
    }

    @Override
    public void delete(Integer id) {
        attributeRepo.deleteById(id);
    }

    @Override
    public Attribute create(Attribute attribute) {
        return attributeRepo.save(attribute);
    }

    @Override
    public Optional<Attribute> find(Integer id) {
        return attributeRepo.findById(id);
    }

    @Override
    public Optional<Attribute> update(Attribute attribute) {
        Optional<Attribute> existed = attributeRepo.findById(attribute.getId());
        if (existed.isPresent()) {
            existed = Optional.of(attributeRepo.save(attribute));
        }
        return existed;
    }
}
