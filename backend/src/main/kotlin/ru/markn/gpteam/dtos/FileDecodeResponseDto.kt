package ru.markn.gpteam.dtos

import jakarta.validation.constraints.NotBlank
import kotlinx.serialization.Serializable

@Serializable
data class FileDecodeResponseDto(
    @field:NotBlank(message = "Filename cannot be empty")
    val filename: String,
    @field:NotBlank(message = "Content cannot be empty")
    val content: String
)
