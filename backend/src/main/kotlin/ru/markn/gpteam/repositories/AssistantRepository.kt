package ru.markn.gpteam.repositories

import org.springframework.data.jpa.repository.JpaRepository
import ru.markn.gpteam.models.Assistant
import java.util.Optional

interface AssistantRepository : JpaRepository<Assistant, Long> {
    fun getByName(name: String): Assistant
    fun findByName(name: String): Optional<Assistant>
    fun findAssistantsByNameContains(name: String): List<Assistant>
    fun existsByName(name: String): Boolean
}