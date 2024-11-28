package ru.markn.gpteam.dtos

import kotlinx.serialization.Serializable

@Serializable
data class ChatCompChoiceDto(
    val message: ChatMessageDto,
)
