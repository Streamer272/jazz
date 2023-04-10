package dev.svitan.routes

import dev.svitan.plugins.GenericResponse
import dev.svitan.plugins.HttpBadRequest
import dev.svitan.plugins.HttpNotFound
import dev.svitan.services.NewNote
import dev.svitan.services.NoteService
import dev.svitan.services.UpdateNote
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.configureNotesRouting() {
    val service = NoteService()

    routing {
        route("/notes") {
            get("/") {
                call.respond(service.getAll())
            }

            get("/{id}") {
                val id = call.parameters["id"] ?: throw HttpBadRequest("ID not provided")
                val note = service.get(id.toInt()) ?: throw HttpNotFound("Note not found")
                call.respond(note)
            }

            put("/") {
                val data = call.receive<NewNote>()
                call.respond(GenericResponse(service.create(data)))
            }

            patch("/{id}") {
                val id = call.parameters["id"] ?: throw HttpBadRequest("ID not provided")
                val data = call.receive<UpdateNote>()
                call.respond(GenericResponse(service.update(id.toInt(), data)))
            }

            delete("/{id}") {
                val id = call.parameters["id"] ?: throw HttpBadRequest("ID not provided")
                call.respond(GenericResponse(service.delete(id.toInt())))
            }
        }
    }
}
