package dev.svitan.services

import kotlinx.serialization.Serializable
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.javatime.datetime
import java.time.LocalDateTime

@Serializable
data class NewNote(
    val name: String,
    val content: String
)

@Serializable
data class UpdateNote(
    val name: String? = null,
    val content: String? = null
)

@Serializable
data class Note(
    val id: Int,
    val name: String,
    val content: String,
    val createdAt: String,
    val updatedAt: String
)

class NoteService {
    object Notes : IntIdTable() {
        val name = text("name")
        val content = text("content")
        val createdAt = datetime("createdAt").default(LocalDateTime.now())
        val updatedAt = datetime("updatedAt").default(LocalDateTime.now())
    }

    companion object {
        fun init() {
            transaction {
                SchemaUtils.create(Notes)
            }
        }
    }

    private fun ResultRow.toNote(): Note = Note(
        id = this[Notes.id].value,
        name = this[Notes.name],
        content = this[Notes.content],
        createdAt = this[Notes.createdAt].toString(),
        updatedAt = this[Notes.updatedAt].toString()
    )

    fun create(note: NewNote): Int = transaction {
        Notes.insert {
            it[name] = note.name
            it[content] = note.content
        }[Notes.id].value
    }

    fun getAll(): List<Note> = transaction {
        Notes.selectAll().toList().map { it.toNote() }
    }

    fun get(id: Int): Note? = transaction {
        Notes.select { Notes.id eq id }
            .singleOrNull()
            ?.toNote()
    }

    fun update(id: Int, note: UpdateNote) = transaction {
        Notes.update({ Notes.id eq id }) {
            if (note.name != null) it[name] = note.name
            if (note.content != null) it[content] = note.content
        }
    }

    fun delete(id: Int) = transaction {
        Notes.deleteWhere { Notes.id.eq(id) }
    }
}
