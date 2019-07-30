package lana.thingService.util.objectmapper;

import org.modelmapper.ModelMapper;

public class ObjectMapperImpl implements ObjectMapper {
    private final ModelMapper modelMapper = new ModelMapper();

    @Override
    public <T> T map(Object source, Class<T> destinationType) {
        return modelMapper.map(source, destinationType);
    }

    @Override
    public void map(Object source, Object destination) {
        modelMapper.map(source, destination);
    }
}
