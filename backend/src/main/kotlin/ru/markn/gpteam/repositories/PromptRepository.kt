package ru.markn.gpteam.repositories

import org.springframework.data.jpa.repository.JpaRepository
import ru.markn.gpteam.models.Prompt
import java.util.*

interface PromptRepository : JpaRepository<Prompt, Long> {
    fun findByName(name: String): Optional<Prompt>
}