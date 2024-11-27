package ru.markn.gpteam.servicies

import ru.markn.gpteam.dtos.ChatAssistantDto
import ru.markn.gpteam.dtos.ChatMessageDto

interface AiModelService {
    fun chatCompletion(authHeader: String, chatAssistantDto: ChatAssistantDto): ChatMessageDto
}