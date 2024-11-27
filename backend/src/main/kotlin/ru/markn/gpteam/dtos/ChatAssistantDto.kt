package ru.markn.gpteam.dtos

import java.io.Serializable

data class ChatAssistantDto(
    val messages: List<ChatMessageDto>
) : Serializable
