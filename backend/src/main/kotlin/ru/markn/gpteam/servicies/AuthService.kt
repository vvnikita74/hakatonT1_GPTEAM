package ru.markn.gpteam.servicies

import ru.markn.gpteam.dtos.JwtDto
import ru.markn.gpteam.dtos.UserDto

interface AuthService {
    fun createAuthToken(userDto: UserDto): JwtDto
}