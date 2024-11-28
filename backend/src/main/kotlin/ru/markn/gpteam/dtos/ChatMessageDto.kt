package ru.markn.gpteam.dtos

import com.fasterxml.jackson.annotation.JsonProperty
import jakarta.validation.constraints.NotEmpty
import kotlinx.serialization.Serializable

@Serializable
data class ChatMessageDto(
    @JsonProperty("content")
    @field:NotEmpty(message = "Content cannot be empty")
    val content: String,
    @JsonProperty("role")
    @field:NotEmpty(message = "Role cannot be empty")
    val role: ChatMessageRole
)