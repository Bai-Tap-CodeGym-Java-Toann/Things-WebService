package lana.thingService;

import lana.thingService.util.objectmapper.ObjectMapper;
import lana.thingService.util.objectmapper.ObjectMapperImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class utilitiesConfig {

    @Bean
    // completely unused. for dto converting which will be implemented in th future
    public ObjectMapper objectMapper() {
        return new ObjectMapperImpl();
    }
}
