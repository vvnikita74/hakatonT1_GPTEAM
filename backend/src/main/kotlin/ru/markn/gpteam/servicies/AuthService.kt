package ru.markn.gpteam.servicies

import ru.markn.gpteam.dtos.JwtDto
import ru.markn.gpteam.dtos.AuthDto

interface AuthService {
    fun createAuthToken(authDto: AuthDto): JwtDto
}