package lana.thingService.util.objectmapper;

public interface ObjectMapper {
    <T> T map(Object source, Class<T> destinationType);

    void map(Object source, Object destination);
}
