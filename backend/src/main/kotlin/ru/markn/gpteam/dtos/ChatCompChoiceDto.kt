package ru.markn.gpteam.dtos

import java.io.Serializable

data class ChatCompChoiceDto(
    val message: ChatMessageDto,
) : Serializable
