package lana.thingService.thing;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lana.thingService.attribute.Attribute;
import lana.thingService.attribute.AttributeDeserializer;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "thing")
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
    @JsonDeserialize(using = AttributeDeserializer.class)
    private Attribute attribute;
}
