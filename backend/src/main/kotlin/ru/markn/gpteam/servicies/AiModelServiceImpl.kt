package ru.markn.gpteam.servicies

import org.springframework.stereotype.Service
import ru.markn.gpteam.clients.AiModelClient
import ru.markn.gpteam.dtos.*
import ru.markn.gpteam.exceptions.IncorrectArgumentException

@Service
class AiModelServiceImpl(
    private val aiModelClient: AiModelClient,
    private val assistantService: AssistantService
) : AiModelService {

    override suspend fun chatCompletion(authHeader: String, chatAssistantDto: ChatAssistantDto): ChatMessageDto {
        val apiKeyAssistant = authHeader.replace("Bearer ", "").ifBlank {
            throw IncorrectArgumentException("Token is empty")
        }
        return aiModelClient.chatCompletion(
            chatAssistantDto.messages + assistantService.getAssistantByApiKey(apiKeyAssistant).prompts
        )
    }
}