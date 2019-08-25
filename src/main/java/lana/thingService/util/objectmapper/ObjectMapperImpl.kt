package lana.thingService.util.objectmapper

import org.modelmapper.ModelMapper

class ObjectMapperImpl : ObjectMapper {
    private val modelMapper = ModelMapper()

    override fun <T> map(source: Any, destinationType: Class<T>): T {
        return modelMapper.map(source, destinationType)
    }

    override fun map(source: Any, destination: Any) {
        modelMapper.map(source, destination)
    }
}
