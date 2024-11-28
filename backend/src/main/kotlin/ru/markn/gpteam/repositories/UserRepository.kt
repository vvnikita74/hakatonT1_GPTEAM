package ru.markn.gpteam.repositories

import org.springframework.data.jpa.repository.JpaRepository
import ru.markn.gpteam.models.User
import java.util.Optional

interface UserRepository : JpaRepository<User, Long> {
    fun findByName(name: String): Optional<User>
    fun existsByName(name: String): Boolean
}