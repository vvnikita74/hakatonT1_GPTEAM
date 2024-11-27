package ru.markn.gpteam.dtos

import com.fasterxml.jackson.annotation.JsonProperty

enum class ChatMessageRole {
    @field:JsonProperty("user")
    USER,
    @field:JsonProperty("assistant")
    ASSISTANT,
    @field:JsonProperty("system")
    SYSTEM;

    override fun toString(): String = when (this) {
        USER -> "user"
        ASSISTANT -> "assistant"
        SYSTEM -> "system"
    }
}