package ru.markn.gpteam.dtos

import com.fasterxml.jackson.annotation.JsonProperty
import java.io.Serializable

data class AssistantDto(
    @JsonProperty("name")
    val name: String,
    @JsonProperty("styles")
    val styles: String,
    @JsonProperty("apiKey")
    val apiKey: String,
    @JsonProperty("prompts")
    val prompts: List<ChatMessageDto> = emptyList()
) : Serializable
