package lana.thingService

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.context.annotation.Import

@SpringBootApplication
@Import(io.github.perplexhub.rsql.RSQLConfig::class)
open class ThingApplication

fun main(args: Array<String>) {
    SpringApplication.run(ThingApplication::class.java, *args)
}