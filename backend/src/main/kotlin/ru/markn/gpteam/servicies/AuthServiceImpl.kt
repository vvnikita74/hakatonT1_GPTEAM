package ru.markn.gpteam.servicies

import jakarta.transaction.Transactional
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.stereotype.Service
import ru.markn.gpteam.dtos.JwtDto
import ru.markn.gpteam.dtos.AuthDto
import ru.markn.gpteam.utils.JwtUtil

@Service
@Transactional
class AuthServiceImpl(
    private val assistantService: AssistantService,
    private val jwtUtil: JwtUtil,
    private val authenticationManager: AuthenticationManager,
) : AuthService {

    override fun createAuthToken(authDto: AuthDto): JwtDto {
        authenticationManager.authenticate(
            UsernamePasswordAuthenticationToken(
                authDto.assistant,
                authDto.password
            )
        )
        val userDetails = assistantService.loadUserByUsername(authDto.assistant)
        return JwtDto(jwtUtil.generateToken(userDetails))
    }
}