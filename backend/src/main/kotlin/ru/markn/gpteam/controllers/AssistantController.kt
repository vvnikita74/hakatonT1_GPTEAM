package ru.markn.gpteam.controllers

import jakarta.validation.Valid
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import org.springframework.web.bind.annotation.*
import ru.markn.gpteam.dtos.*
import ru.markn.gpteam.servicies.AiModelService
import ru.markn.gpteam.servicies.AssistantService
import ru.markn.gpteam.utils.JwtUtil
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
        @Valid @NotNull principal: Principal
    ): DetailsAssistantDto =
        assistantService.updateAssistant(
            formDataUpdateAssistantDto.copy(assistant = principal.name).toDetailsDto()
        ).toDetailsDto()

    @PostMapping("/chat")
    suspend fun chatWithAssistant(
        @NotBlank @RequestHeader("Authorization") authorizationHeader: String,
        @Valid @RequestBody chatMessagesDto: ChatAssistantDto
    ): ChatMessageDto =
        aiModelService.chatCompletion(JwtUtil.getKeyFromAuthHeader(authorizationHeader), chatMessagesDto)

    @GetMapping("/chat")
    fun getStylesAssistant(
        @NotBlank @RequestHeader("Authorization") authorizationHeader: String
    ): StylesAssistantDto =
        assistantService.getAssistantByApiKey(JwtUtil.getKeyFromAuthHeader(authorizationHeader)).let {
            StylesAssistantDto(it.name, it.styles)
        }
}