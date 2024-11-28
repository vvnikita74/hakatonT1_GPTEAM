package ru.markn.gpteam.controllers

import jakarta.validation.Valid
import org.springframework.web.bind.annotation.*
import ru.markn.gpteam.dtos.JwtDto
import ru.markn.gpteam.dtos.AuthDto
import ru.markn.gpteam.servicies.AuthService
import ru.markn.gpteam.servicies.AssistantService

@RestController
class AuthController(
    private val authService: AuthService,
    private val assistantService: AssistantService
) {
    @PostMapping("/signIn")
    fun signIn(@Valid @RequestBody authDto: AuthDto): JwtDto = authService.createAuthToken(authDto)

    @PostMapping("/signUp")
    fun signUp(@Valid @RequestBody newAuthDto: AuthDto): JwtDto {
        assistantService.addUser(newAuthDto)
        return authService.createAuthToken(newAuthDto)
    }
}