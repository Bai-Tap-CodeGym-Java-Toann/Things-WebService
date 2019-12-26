package lana.thingService.thing;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lana.thingService.attribute.Attribute;
import lana.thingService.attribute.binding.AttributeJsonDeserializer;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Thing {
    @Id
    @GeneratedValue
    private Integer id;

    private String name;

    private String description;

    //TODO: make new model Generic
    private String generic;

    @ManyToOne
    @JoinColumn
    // to binding attribute from id.
    @JsonDeserialize(using = AttributeJsonDeserializer.class)
    private Attribute attribute;
}
