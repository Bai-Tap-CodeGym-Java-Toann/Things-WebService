package lana.thingService;

import lana.thingService.util.objectmapper.ObjectMapper;
import lana.thingService.util.objectmapper.ObjectMapperImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class utilitiesConfig {
    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapperImpl();
    }
}
