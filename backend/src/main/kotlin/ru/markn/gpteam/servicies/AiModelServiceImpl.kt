package ru.markn.gpteam.servicies

import org.springframework.stereotype.Service
import ru.markn.gpteam.clients.AiModelClient
import ru.markn.gpteam.dtos.*

@Service
class AiModelServiceImpl(
    private val aiModelClient: AiModelClient,
    private val assistantService: AssistantService
) : AiModelService {

    override suspend fun chatCompletion(apiKey: String, chatAssistantDto: ChatAssistantDto): ChatMessageDto =
        aiModelClient.chatCompletion(
            chatAssistantDto.messages + assistantService.getAssistantByApiKey(apiKey).prompts
        )
}