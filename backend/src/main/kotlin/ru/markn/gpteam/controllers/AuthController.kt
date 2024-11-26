package ru.markn.gpteam.controllers

import jakarta.validation.Valid
import org.springframework.web.bind.annotation.*
import ru.markn.gpteam.dtos.JwtDto
import ru.markn.gpteam.dtos.AssistantDto
import ru.markn.gpteam.servicies.AuthService
import ru.markn.gpteam.servicies.AssistantService

@RestController
class AuthController(
    private val authService: AuthService,
    private val assistantService: AssistantService
) {
    @PostMapping("/signIn")
    fun signIn(@Valid @RequestBody assistantDto: AssistantDto): JwtDto = authService.createAuthToken(assistantDto)

    @PostMapping("/signUp")
    fun signUp(@Valid @RequestBody newAssistantDto: AssistantDto): JwtDto {
        assistantService.addAssistant(newAssistantDto)
        return authService.createAuthToken(newAssistantDto)
    }
}