package lana.thingService.util.objectmapper

interface ObjectMapper {

    fun <T> map(source: Any, destinationType: Class<T>): T

    fun map(source: Any, destination: Any)
}
