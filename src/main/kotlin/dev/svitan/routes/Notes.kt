package dev.svitan.routes

import dev.svitan.services.NoteService
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.configureNotesRouting() {
    val service = NoteService()

    routing {
        route("/notes") {
            get("/") {
                call.respond(service.getAll())
            }
        }
    }
}
