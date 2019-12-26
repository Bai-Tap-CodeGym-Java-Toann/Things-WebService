package lana.thingService;

import lana.thingService.attribute.Attribute;
import lana.thingService.attribute.AttributeRepo;
import lana.thingService.thing.Thing;
import lana.thingService.thing.ThingRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ThingApplication {

    public static void main(String[] args) {
        SpringApplication.run(ThingApplication.class, args);
    }

    @Bean
    @Autowired
    public CommandLineRunner insertData(ThingRepo thingRepo, AttributeRepo attributeRepo) {
        return (args) -> {
            Attribute water = attributeRepo.save(new Attribute("Water", "Some lulquid"));
            Attribute fire = attributeRepo.save(new Attribute("File", "Ops, I meant Fire"));
            attributeRepo.save(new Attribute("Paleoproterozoic Air", "Oxygen not included"));

            thingRepo.save(new Thing("Sea water", water));
            thingRepo.save(new Thing("River water", water));
            thingRepo.save(new Thing("Drinking water", water));
            thingRepo.save(new Thing("Sea fire", fire));
        };
    }
}
