package ru.markn.gpteam.dtos

import jakarta.validation.constraints.NotBlank
import java.io.Serializable

data class DetailsAssistantDto(
    @field:NotBlank(message = "Assistant name cannot be empty")
    val name: String,
    @field:NotBlank(message = "Styles cannot be empty")
    val styles: String,
    val text: String = "",
    val files: List<FileAssistantDto> = emptyList()
) : Serializable