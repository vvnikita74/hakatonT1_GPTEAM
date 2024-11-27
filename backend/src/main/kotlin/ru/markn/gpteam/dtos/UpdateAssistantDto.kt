package ru.markn.gpteam.dtos

import org.springframework.web.multipart.MultipartFile
import ru.markn.gpteam.utils.NotEmptyOrNull
import java.io.Serializable

data class UpdateAssistantDto(
    val assistant: String?,
    @field:NotEmptyOrNull(message = "Files cannot be empty")
    val files: List<MultipartFile>?,
    @field:NotEmptyOrNull(message = "Styles cannot be empty")
    val styles: String?,
    val text: String?,
    val deletedFiles: List<FileAssistantDto>?
) : Serializable