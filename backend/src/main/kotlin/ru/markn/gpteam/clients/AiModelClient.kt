package ru.markn.gpteam.clients

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import ru.markn.gpteam.dtos.ChatCompRequestDto
import ru.markn.gpteam.dtos.ChatCompResponseDto
import ru.markn.gpteam.exceptions.ExternalServiceRequestException
import ru.markn.gpteam.dtos.ChatMessageDto

@Component
class AiModelClient(
    @Value("\${ai.url}")
    private val aiUrl: String,
    @Value("\${ai.apikey}")
    private val apiKey: String
) {
    companion object {
        private const val AI_MODEL = "mistral-large-latest"
    }

    private val client: HttpClient = HttpClient {
        install(ContentNegotiation) {
            json(
                json = Json {
                    ignoreUnknownKeys = true
                    prettyPrint = true
                },
                contentType = ContentType.Application.Json
            )
        }
        defaultRequest {
            header("Authorization", "Bearer $apiKey")
        }
    }

    suspend fun chatCompletion(chatMessages: List<ChatMessageDto>): ChatMessageDto =
        ChatCompRequestDto(
            model = AI_MODEL,
            messages = chatMessages
        ).let {
            runCatching {
                client.post(aiUrl) {
                    contentType(ContentType.Application.Json)
                    setBody(it)
                }.body<ChatCompResponseDto>()
            }.getOrElse {
                throw ExternalServiceRequestException("Chat completion failed: ${it.message}")
            }.choices.first().message
        }
}