package ru.markn.gpteam.dtos

import jakarta.validation.constraints.NotBlank
import java.io.Serializable

data class JwtDto(
    @field:NotBlank(message = "Username cannot be empty")
    val token: String,
) : Serializable