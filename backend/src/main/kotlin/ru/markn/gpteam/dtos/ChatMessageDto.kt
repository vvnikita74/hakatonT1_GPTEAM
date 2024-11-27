package ru.markn.gpteam.dtos

import jakarta.validation.constraints.NotEmpty
import java.io.Serializable

data class ChatMessageDto(
    @field:NotEmpty(message = "Content cannot be empty")
    val content: String,
    @field:NotEmpty(message = "Role cannot be empty")
    val role: ChatMessageRole
) : Serializable