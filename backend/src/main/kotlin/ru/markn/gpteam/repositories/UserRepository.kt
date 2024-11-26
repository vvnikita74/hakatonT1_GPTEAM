package ru.markn.gpteam.repositories

import org.springframework.data.jpa.repository.JpaRepository
import ru.markn.gpteam.models.User
import java.util.Optional

interface UserRepository : JpaRepository<User, Long> {
    fun findByUsername(username: String): Optional<User>
    fun findUsersByUsernameIn(usernames: List<String>): List<User>
    fun findUsersByUsernameContains(username: String): List<User>
    fun existsByUsername(username: String): Boolean
}