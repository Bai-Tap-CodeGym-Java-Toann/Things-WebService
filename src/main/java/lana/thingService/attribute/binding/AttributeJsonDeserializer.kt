package lana.thingService.attribute.binding

import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.core.JsonToken
import com.fasterxml.jackson.databind.DeserializationContext
import com.fasterxml.jackson.databind.deser.std.StdDeserializer
import lana.thingService.attribute.Attribute
import lana.thingService.attribute.AttributeRepo
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class AttributeJsonDeserializer : StdDeserializer<Attribute> {
    private lateinit var attributeRepo: AttributeRepo

    constructor() : this(null)
    constructor(vc: Class<Any>?) : super(vc)

    @Autowired
    fun setUpAttributeHandle(attributeRepo: AttributeRepo) {
        this.attributeRepo = attributeRepo
    }

    override fun deserialize(p: JsonParser, ctxt: DeserializationContext): Attribute? {
        val attributeId = when {
            p.currentToken == JsonToken.VALUE_STRING -> Integer.parseInt(p.text)
            p.currentToken == JsonToken.VALUE_NUMBER_INT -> p.numberValue.toInt()
            else -> return null
        }
        return attributeRepo.findById(attributeId).orElse(null)
    }
}
