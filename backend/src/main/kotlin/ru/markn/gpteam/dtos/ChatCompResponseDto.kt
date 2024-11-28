package ru.markn.gpteam.dtos

import kotlinx.serialization.Serializable

@Serializable
data class ChatCompResponseDto(
    val choices: List<ChatCompChoiceDto>
)
