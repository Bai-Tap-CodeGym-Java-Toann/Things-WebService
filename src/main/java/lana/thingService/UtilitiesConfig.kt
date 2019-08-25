package lana.thingService

import lana.thingService.util.objectmapper.ObjectMapper
import lana.thingService.util.objectmapper.ObjectMapperImpl
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
open class UtilitiesConfig {

    @Bean
    // completely unused. for dto converting which will be implemented in th future
    open fun objectMapper(): ObjectMapper {
        return ObjectMapperImpl()
    }
}
