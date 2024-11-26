package ru.markn.gpteam.dtos

import jakarta.validation.constraints.NotBlank
import java.io.Serializable

data class AssistantDto(
    @field:NotBlank(message = "Assistant name cannot be empty")
    val assistant: String,

    @field:NotBlank(message = "Password cannot be empty")
    val password: String
) : Serializable
