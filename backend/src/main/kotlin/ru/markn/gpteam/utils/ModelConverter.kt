package ru.markn.gpteam.utils

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import ru.markn.gpteam.dtos.DetailsAssistantDto
import ru.markn.gpteam.dtos.FileAssistantDto
import ru.markn.gpteam.dtos.FormDataUpdateAssistantDto
import ru.markn.gpteam.dtos.UpdateAssistantDto
import ru.markn.gpteam.exceptions.IncorrectArgumentException
import ru.markn.gpteam.models.Assistant
import ru.markn.gpteam.models.Prompt

fun Prompt.toDto() = FileAssistantDto(
    id = id,
    filename = name
)

fun Assistant.toDto() = DetailsAssistantDto(
    name = name,
    apiKey = apiKey,
    styles = styles,
    text = prompts.find { it.name == "text" }?.content ?: "",
    files = prompts.filterNot { it.name == "text" }.map { it.toDto() }
)

fun FormDataUpdateAssistantDto.toDto() = UpdateAssistantDto(
    assistant = assistant,
    styles = styles,
    text = text,
    files = files,
    deletedFiles = runCatching {
        deletedFiles?.let {
            jacksonObjectMapper().readValue(it, Array<FileAssistantDto>::class.java).toList()
        }
    }.getOrElse {
        throw IncorrectArgumentException("Incorrect deleted files format")
    }
)
