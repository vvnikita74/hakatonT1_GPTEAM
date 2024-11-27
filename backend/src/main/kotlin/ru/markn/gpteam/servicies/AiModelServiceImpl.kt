package ru.markn.gpteam.servicies

import org.springframework.security.authentication.BadCredentialsException
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient
import ru.markn.gpteam.dtos.*
import ru.markn.gpteam.exceptions.ExternalServiceRequestException


@Service
class AiModelServiceImpl(
    private val aiModelClient: WebClient,
    private val assistantService: AssistantService
) : AiModelService {
    companion object {
        private const val AI_MODEL = "mistral-large-latest"
    }

    override fun chatCompletion(authHeader: String, chatAssistantDto: ChatAssistantDto): ChatMessageDto {
        val apiKeyAssistant = authHeader.replace("Bearer ", "").ifBlank {
            throw BadCredentialsException("Token is empty")
        }
        val messagePrompts = assistantService.getAssistantByApiKey(apiKeyAssistant).prompts.let {
            chatAssistantDto.messages.toMutableList().apply {
                addAll(it.map { prompt -> ChatMessageDto(prompt.content, ChatMessageRole.SYSTEM) })
            }.toList()
        }
        val chatCompRequestDto = ChatCompRequestDto(
            model = AI_MODEL,
            messages = messagePrompts
        )
        return runCatching {
            aiModelClient.post()
                .bodyValue(chatCompRequestDto)
                .retrieve()
                .bodyToMono(ChatCompResponseDto::class.java)
                .block() ?: throw BadCredentialsException("Chat completion is empty")
        }.getOrElse {
            throw ExternalServiceRequestException("Chat completion failed: ${it.message}")
        }.choices.first().message
    }
}