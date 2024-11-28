package ru.markn.gpteam.utils

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import ru.markn.gpteam.dtos.*
import ru.markn.gpteam.exceptions.IncorrectArgumentException
import ru.markn.gpteam.models.Assistant
import ru.markn.gpteam.models.Prompt

fun Prompt.toDetailsDto() = FileAssistantDto(
    id = id,
    filename = name
)

fun Assistant.toDto() = AssistantDto(
    name = user.name,
    apiKey = apiKey,
    styles = styles,
    prompts = prompts.map { ChatMessageDto(content = it.content, role = ChatMessageRole.SYSTEM) }
)

fun Assistant.toDetailsDto() = DetailsAssistantDto(
    name = user.name,
    apiKey = apiKey,
    styles = styles,
    text = prompts.find { it.name == "text" }?.content ?: "",
    files = prompts.filterNot { it.name == "text" }.map { it.toDetailsDto() }
)

fun FormDataUpdateAssistantDto.toDetailsDto() = UpdateAssistantDto(
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
