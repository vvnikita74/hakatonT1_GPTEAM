package ru.markn.gpteam.dtos

import jakarta.validation.constraints.NotBlank
import java.io.Serializable

data class UserDto(
    @field:NotBlank(message = "Username cannot be empty")
    val username: String,

    @field:NotBlank(message = "Password cannot be empty")
    val password: String
) : Serializable
