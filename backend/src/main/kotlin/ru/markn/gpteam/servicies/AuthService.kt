package ru.markn.gpteam.servicies

import ru.markn.gpteam.dtos.JwtDto
import ru.markn.gpteam.dtos.AssistantDto

interface AuthService {
    fun createAuthToken(assistantDto: AssistantDto): JwtDto
}