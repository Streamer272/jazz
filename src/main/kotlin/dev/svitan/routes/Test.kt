package dev.svitan.routes

import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.configureTestRouting() {
    routing {
        route("/test") {
            get("/") {
                call.respond("Hello World!")
            }

            get("/json") {
                call.respond(mapOf("response" to "Hello World!"))
            }
        }
    }
}
