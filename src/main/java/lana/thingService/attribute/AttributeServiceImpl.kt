package lana.thingService.attribute

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service

@Service("attributeService")
class AttributeServiceImpl
@Autowired
constructor(private val attributeRepo: AttributeRepo) : AttributeService {

    override fun findAll(pageable: Pageable): Page<Attribute> {
        return attributeRepo.findAll(pageable)
    }

    override fun find(id: Int): Attribute? {
        return attributeRepo.findById(id).get()
    }

    override fun delete(id: Int) {
        attributeRepo.deleteById(id)
    }

    override fun create(attribute: Attribute): Attribute? {
        return if (!isExist(attribute)) attributeRepo.save(attribute) else null
    }

    override fun update(attribute: Attribute): Attribute? {
        return if (isExist(attribute)) attributeRepo.save(attribute) else null
    }

    private fun isExist(attribute: Attribute): Boolean {
        val attributeId = attribute.id ?: return false
        return attributeRepo.existsById(attributeId)
    }
}
