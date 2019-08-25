package lana.thingService

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication

@SpringBootApplication
open class ThingApplication

fun main(args: Array<String>) {
    SpringApplication.run(ThingApplication::class.java, *args)
}