package lana.thingService.thing

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service

@Service("thingService")
class ThingServiceImpl
@Autowired
constructor(private val thingRepo: ThingRepo) : ThingService {

    override fun findAll(pageable: Pageable): Page<Thing> {
        return thingRepo.findAll(pageable)
    }

    override fun find(id: Int): Thing? {
        return thingRepo.findById(id).get()
    }


    override fun delete(id: Int) {
        thingRepo.deleteById(id)
    }

    override fun create(thing: Thing): Thing? {
        return if (!isExist(thing)) thingRepo.save(thing) else null
    }

    override fun update(thing: Thing): Thing? {
        return if (isExist(thing)) thingRepo.save(thing) else null
    }

    private fun isExist(thing: Thing): Boolean {
        val thingId = thing.id ?: return false
        return thingRepo.existsById(thingId)
    }
}
