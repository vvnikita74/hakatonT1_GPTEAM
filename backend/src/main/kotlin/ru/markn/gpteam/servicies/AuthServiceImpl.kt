package ru.markn.gpteam.servicies

import jakarta.transaction.Transactional
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.stereotype.Service
import ru.markn.gpteam.dtos.JwtDto
import ru.markn.gpteam.dtos.UserDto
import ru.markn.gpteam.utils.JwtUtil

@Service
@Transactional
class AuthServiceImpl(
    private val userService: UserService,
    private val jwtUtil: JwtUtil,
    private val authenticationManager: AuthenticationManager,
) : AuthService {

    override fun createAuthToken(userDto: UserDto): JwtDto {
        authenticationManager.authenticate(
            UsernamePasswordAuthenticationToken(
                userDto.username,
                userDto.password
            )
        )
        val userDetails = userService.loadUserByUsername(userDto.username)
        return JwtDto(jwtUtil.generateToken(userDetails))
    }
}