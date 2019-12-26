package lana.thingService.attribute;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lana.thingService.thing.Thing;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
// prevent infinite loop when convert to json
@JsonIgnoreProperties(value = {"things"})
public class Attribute {
    @Id
    @GeneratedValue
    private Integer id;

    private String name;

    private String description;

    @OneToMany(mappedBy = "attribute")
    private List<Thing> things;
}
