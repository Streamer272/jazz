package dev.svitan.plugins

import dev.svitan.routes.configureNotesRouting
import dev.svitan.routes.configureTestRouting
import io.ktor.server.application.*

fun Application.configureRouting() {
    configureTestRouting()
    configureNotesRouting()
}
