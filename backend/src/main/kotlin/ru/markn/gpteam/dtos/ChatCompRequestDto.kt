package ru.markn.gpteam.dtos

import kotlinx.serialization.Serializable

@Serializable
data class ChatCompRequestDto(
    val model: String,
    val messages: List<ChatMessageDto>
)
