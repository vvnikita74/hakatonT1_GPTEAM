package ru.markn.gpteam.dtos

import java.io.Serializable

data class ChatCompResponseDto(
    val choices: List<ChatCompChoiceDto>
) : Serializable
