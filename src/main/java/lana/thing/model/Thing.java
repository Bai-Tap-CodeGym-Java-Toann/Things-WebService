package lana.thing.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lana.thing.model.deserializer.AttributeHandle;

import javax.persistence.*;

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
    @JsonDeserialize(using = AttributeHandle.class)
    private Attribute attribute;

    public Thing() {
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

    public Attribute getAttribute() {
        return attribute;
    }

    public void setAttribute(Attribute attribute) {
        this.attribute = attribute;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getGeneric() {
        return generic;
    }

    public void setGeneric(String generic) {
        this.generic = generic;
    }
}
