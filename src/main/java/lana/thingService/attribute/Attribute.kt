package lana.thingService.attribute

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import lana.thingService.thing.Thing

import javax.persistence.*

@Entity
@Table(name = "attribute")
// prevent infinite loop when convert to json
@JsonIgnoreProperties(value = ["things"])
class Attribute(
        @Id
        @GeneratedValue
        var id: Int? = null,

        var name: String = "",

        var description: String = "",

        @OneToMany(mappedBy = "attribute")
        var things: List<Thing> = emptyList()
)
