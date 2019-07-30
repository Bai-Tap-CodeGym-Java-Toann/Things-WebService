package lana.thingService.attribute.binding;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import lana.thingService.attribute.Attribute;
import lana.thingService.attribute.AttributeRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class AttributeDeserializer extends StdDeserializer<Attribute> {
    private AttributeRepo attributeRepo;

    public AttributeDeserializer() {
        this(null);
    }

    public AttributeDeserializer(Class<?> vc) {
        super(vc);
    }

    @Autowired
    public void setUpAttributeHandle(AttributeRepo attributeRepo) {
        this.attributeRepo = attributeRepo;
    }


    @Override
    public Attribute deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        int attributeId;
        if (p.getCurrentToken() == JsonToken.VALUE_STRING) {
            attributeId = Integer.parseInt(p.getText());
        } else if (p.getCurrentToken() == JsonToken.VALUE_NUMBER_INT) {
            attributeId = p.getNumberValue().intValue();
        } else {
            return null;
        }
        return attributeRepo.findById(attributeId).orElse(null);
    }
}
