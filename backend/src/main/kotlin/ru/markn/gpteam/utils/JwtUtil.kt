package ru.markn.gpteam.utils

import io.jsonwebtoken.*
import io.jsonwebtoken.io.Decoders
import io.jsonwebtoken.security.Keys
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Component
import ru.markn.gpteam.exceptions.IncorrectArgumentException
import java.security.Key
import java.time.Duration
import java.util.*
import javax.crypto.SecretKey

@Component
class JwtUtil(
    @Value("\${jwt.secret}")
    private val secret: String,
    @Value("\${jwt.lifetime}")
    private val jwtLifetime: Duration
) {
    private val signingKey: Key
        get() = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secret))

    companion object {
        private val LOGGER = LoggerFactory.getLogger(JwtUtil::class.java)

        fun getKeyFromAuthHeader(authHeader: String): String = authHeader
            .replace("Bearer ", "")
            .ifBlank { throw IncorrectArgumentException("Token is empty") }
    }

    fun generateToken(userDetails: UserDetails): String {
        val claims: MutableMap<String, Any?> = HashMap()
        val rolesList = userDetails.authorities.map(GrantedAuthority::getAuthority)
        claims["roles"] = rolesList
        val issuedDate = Date()
        val expiredDate = Date(issuedDate.time + jwtLifetime.toMillis())
        return Jwts.builder()
            .header().add(mapOf("typ" to "JWT")).and()
            .claims(claims)
            .subject(userDetails.username)
            .issuedAt(issuedDate)
            .expiration(expiredDate)
            .signWith(signingKey)
            .compact()
    }

    fun getUsernameFromToken(token: String): String {
        getClaims(token).onSuccess { return it.subject }
        return ""
    }

    fun getRolesFromToken(token: String): List<String> {
        getClaims(token).onSuccess {
            it["roles"]?.let { roles ->
                if (roles is List<*>) {
                    return roles.filterIsInstance<String>()
                }
            }
        }
        return emptyList()
    }

    private fun getClaims(token: String): Result<Claims> {
        try {
            return Result.success(
                Jwts.parser()
                    .verifyWith(signingKey as SecretKey)
                    .build()
                    .parseSignedClaims(token)
                    .payload
            )
        } catch (ex: Exception) {
            LOGGER.warn(ex.message)
            return Result.failure(ex)
        }
    }
}