package ru.markn.gpteam.dtos

import java.io.Serializable

data class StylesAssistantDto(
    val assistant: String,
    val styles: String
) : Serializable
