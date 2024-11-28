package ru.markn.gpteam.repositories

import org.springframework.data.jpa.repository.JpaRepository
import ru.markn.gpteam.models.Assistant
import java.util.Optional

interface AssistantRepository : JpaRepository<Assistant, Long> {
    fun findByUserName(name: String): Optional<Assistant>
    fun findByApiKey(apiKey: String): Optional<Assistant>
}