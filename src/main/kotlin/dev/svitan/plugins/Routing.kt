package dev.svitan.plugins

import dev.svitan.routes.configureNotesRouting
import io.ktor.server.application.*

fun Application.configureRouting() {
    configureNotesRouting()
}
