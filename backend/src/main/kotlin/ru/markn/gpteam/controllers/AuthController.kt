package ru.markn.gpteam.controllers

import jakarta.validation.Valid
import org.springframework.web.bind.annotation.*
import ru.markn.gpteam.dtos.JwtDto
import ru.markn.gpteam.dtos.UserDto
import ru.markn.gpteam.servicies.AuthService
import ru.markn.gpteam.servicies.UserService

@RestController
class AuthController(
    private val authService: AuthService,
    private val userService: UserService
) {
    @PostMapping("/signIn")
    fun signIn(@Valid @RequestBody userDto: UserDto): JwtDto = authService.createAuthToken(userDto)

    @PostMapping("/signUp")
    fun signUp(@Valid @RequestBody newUserDto: UserDto): JwtDto {
        userService.addUser(newUserDto)
        return authService.createAuthToken(newUserDto)
    }
}