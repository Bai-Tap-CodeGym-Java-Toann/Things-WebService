package lana.thingService.attribute;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


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
    public Attribute create(Attribute attribute) throws AttributeExistedException {
        if (isExist(attribute)) throw new AttributeExistedException();
        return attributeRepo.save(attribute);
    }

    private boolean isExist(Attribute attribute) {
        Integer attributeId = attribute.getId();
        if (attributeId == null) return false;
        return attributeRepo.existsById(attributeId);
    }

    @Override
    public Attribute find(Integer id) throws AttributeNotFoundException {
        return attributeRepo.findById(id).orElseThrow(AttributeNotFoundException::new);
    }

    @Override
    public Attribute update(Attribute attribute) throws AttributeNotFoundException {
        attribute = attributeRepo.findById(attribute.getId()).orElseThrow(AttributeNotFoundException::new);
        return attributeRepo.save(attribute);
    }
}
