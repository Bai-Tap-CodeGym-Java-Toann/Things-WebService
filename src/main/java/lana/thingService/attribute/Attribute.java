package lana.thingService.attribute;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lana.thingService.thing.Thing;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "attribute")
@JsonIgnoreProperties(value = {"things"})
public class Attribute {
    @Id
    @GeneratedValue
    private Integer id;

    private String name;

    private String description;

    @OneToMany(mappedBy = "attribute")
    private List<Thing> things;

    public Attribute() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    public List<Thing> getThings() {
        return things;
    }


    public void setThings(List<Thing> things) {
        this.things = things;
    }
}
