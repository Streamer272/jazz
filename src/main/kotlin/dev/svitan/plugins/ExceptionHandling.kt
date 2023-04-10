package dev.svitan.plugins

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.plugins.statuspages.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import java.time.LocalDateTime

data class HttpException(
    val code: Int,
    override val message: String,
    val description: String? = null
) : Exception(message)

fun HttpBadRequest(message: String, description: String? = null) =
    HttpException(
        HttpStatusCode.BadRequest.value,
        message,
        description
    )

fun HttpNotFound(message: String, description: String? = null) =
    HttpException(
        HttpStatusCode.NotFound.value,
        message,
        description
    )

data class HttpExceptionResponse(
    val code: Int,
    val path: String,
    val timestamp: String,
    val message: String,
    val description: String?
)

fun Application.configureExceptionHandling() {
    install(StatusPages) {
        exception<HttpException> { call, cause ->
            call.respond(
                HttpStatusCode.fromValue(cause.code),
                HttpExceptionResponse(
                    cause.code,
                    call.request.path(),
                    LocalDateTime.now().toString(),
                    cause.message,
                    cause.description
                )
            )
        }

        exception<Throwable> { call, cause ->
            call.respond(
                HttpStatusCode.InternalServerError,
                HttpExceptionResponse(
                    HttpStatusCode.InternalServerError.value,
                    call.request.path(),
                    LocalDateTime.now().toString(),
                    cause.message ?: "Unknown error",
                    null
                )
            )
        }
    }
}
