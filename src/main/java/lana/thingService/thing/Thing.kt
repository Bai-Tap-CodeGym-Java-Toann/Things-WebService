package lana.thingService.thing;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import lana.thingService.attribute.Attribute
import lana.thingService.attribute.binding.AttributeJsonDeserializer

import javax.persistence.*

@Entity
@Table(name = "thing")
class Thing(
        @Id
        @GeneratedValue
        var id: Int? = null,

        var name: String = "",

        var description: String = "",

        var generic: String = "",

        @ManyToOne
        @JoinColumn
        @JsonDeserialize(using = AttributeJsonDeserializer::class)
        var attribute: Attribute? = null
)