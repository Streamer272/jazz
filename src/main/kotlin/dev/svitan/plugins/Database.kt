package dev.svitan.plugins

import dev.svitan.services.NoteService
import io.ktor.server.application.*
import org.jetbrains.exposed.sql.Database

fun Application.configureDatabase() {
    Database.connect(
        url = "jdbc:postgresql://localhost:5432/db",
        driver = "org.postgresql.Driver",
        user = "user",
        password = "password123"
    )
    NoteService.init()
}
