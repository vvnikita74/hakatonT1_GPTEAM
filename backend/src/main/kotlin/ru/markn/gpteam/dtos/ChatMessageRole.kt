package ru.markn.gpteam.dtos

import com.fasterxml.jackson.annotation.JsonProperty
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
enum class ChatMessageRole {
    @SerialName("user")
    @field:JsonProperty("user")
    USER,

    @SerialName("assistant")
    @field:JsonProperty("assistant")
    ASSISTANT,

    @SerialName("system")
    @field:JsonProperty("system")
    SYSTEM;

    override fun toString(): String = when (this) {
        USER -> "user"
        ASSISTANT -> "assistant"
        SYSTEM -> "system"
    }
}