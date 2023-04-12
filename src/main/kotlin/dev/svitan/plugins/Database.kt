package dev.svitan.plugins

import dev.svitan.services.NoteService
import io.ktor.server.application.*
import org.jetbrains.exposed.sql.Database

// tf?
fun Application.configureDatabase() {
    Database.connect(
        url = "jdbc:postgresql://35.227.189.234/default",
        driver = "org.postgresql.Driver",
        user = "user",
        password = "password123"
    )
    NoteService.init()
}
