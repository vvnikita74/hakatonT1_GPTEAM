package ru.markn.gpteam.dtos

import org.springframework.web.multipart.MultipartFile
import ru.markn.gpteam.utils.NotEmptyOrNull
import java.io.Serializable

data class FormDataUpdateAssistantDto(
    val assistant: String?,
    @field:NotEmptyOrNull(message = "Files cannot be empty")
    val files: List<MultipartFile>?,
    @field:NotEmptyOrNull(message = "Styles cannot be empty")
    val styles: String?,
    val text: String?,
    val deletedFiles: String?
) : Serializable