package ru.markn.gpteam.dtos

import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotBlank
import java.io.Serializable

data class FileAssistantDto(
    @field:Min(value = 1, message = "File ID must be greater than 0")
    val id: Long,
    @field:NotBlank(message = "Filename cannot be empty")
    val filename: String,
) : Serializable
