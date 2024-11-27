package ru.markn.gpteam.dtos

import java.io.Serializable

data class ChatCompRequestDto(
    val model: String,
    val messages: List<ChatMessageDto>
) : Serializable
