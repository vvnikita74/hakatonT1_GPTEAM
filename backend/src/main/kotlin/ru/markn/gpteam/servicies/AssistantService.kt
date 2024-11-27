package ru.markn.gpteam.servicies

import org.springframework.security.core.userdetails.UserDetailsService
import ru.markn.gpteam.dtos.AuthDto
import ru.markn.gpteam.dtos.UpdateAssistantDto
import ru.markn.gpteam.models.Assistant

interface AssistantService : UserDetailsService {
    fun getAssistantById(id: Long): Assistant
    fun getAssistantByName(name: String): Assistant
    fun getAssistantByApiKey(apiKey: String): Assistant
    fun addAssistant(authDto: AuthDto): Assistant
    fun updateAssistant(updateAssistantDto: UpdateAssistantDto): Assistant
    fun deleteAssistant(id: Long)
}