package ru.markn.gpteam.controllers

import jakarta.validation.Valid
import jakarta.validation.constraints.NotBlank
import org.springframework.web.bind.annotation.*
import ru.markn.gpteam.dtos.ChatAssistantDto
import ru.markn.gpteam.dtos.ChatMessageDto
import ru.markn.gpteam.dtos.DetailsAssistantDto
import ru.markn.gpteam.dtos.FormDataUpdateAssistantDto
import ru.markn.gpteam.servicies.AiModelService
import ru.markn.gpteam.servicies.AssistantService
import ru.markn.gpteam.utils.toDetailsDto
import java.security.Principal

@RestController
@RequestMapping
class AssistantController(
    private val assistantService: AssistantService,
    private val aiModelService: AiModelService
) {
    @GetMapping("/assistant")
    fun getAssistant(principal: Principal): DetailsAssistantDto =
        assistantService.getAssistantByName(principal.name).toDetailsDto()

    @PostMapping("/assistant")
    fun saveAssistant(
        @Valid @ModelAttribute formDataUpdateAssistantDto: FormDataUpdateAssistantDto,
        principal: Principal
    ): DetailsAssistantDto =
        assistantService.updateAssistant(
            formDataUpdateAssistantDto.copy(assistant = principal.name).toDetailsDto()
        ).toDetailsDto()

    @PostMapping("/chat")
    suspend fun chatWithAssistant(
        @NotBlank @RequestHeader("Authorization") authorizationHeader: String,
        @Valid @RequestBody chatMessagesDto: ChatAssistantDto
    ): ChatMessageDto = aiModelService.chatCompletion(authorizationHeader, chatMessagesDto)
}