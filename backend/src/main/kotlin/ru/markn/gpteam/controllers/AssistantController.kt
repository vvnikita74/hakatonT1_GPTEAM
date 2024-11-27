package ru.markn.gpteam.controllers

import jakarta.validation.Valid
import org.springframework.web.bind.annotation.*
import ru.markn.gpteam.dtos.DetailsAssistantDto
import ru.markn.gpteam.dtos.FormDataUpdateAssistantDto
import ru.markn.gpteam.servicies.AssistantService
import ru.markn.gpteam.utils.toDto
import java.security.Principal

@RestController
@RequestMapping("/assistant")
class AssistantController(
    private val assistantService: AssistantService
) {
    @GetMapping
    fun getAssistant(principal: Principal): DetailsAssistantDto =
        assistantService.getAssistantByName(principal.name).toDto()

    @PostMapping
    fun saveAssistant(
        @Valid @ModelAttribute formDataUpdateAssistantDto: FormDataUpdateAssistantDto,
        principal: Principal
    ): DetailsAssistantDto =
        assistantService.updateAssistant(
            formDataUpdateAssistantDto.copy(assistant = principal.name).toDto()
        ).toDto()
}