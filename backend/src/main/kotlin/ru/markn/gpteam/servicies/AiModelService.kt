package ru.markn.gpteam.servicies

import ru.markn.gpteam.dtos.ChatAssistantDto
import ru.markn.gpteam.dtos.ChatMessageDto

interface AiModelService {
    suspend fun chatCompletion(apiKey: String, chatAssistantDto: ChatAssistantDto): ChatMessageDto
}