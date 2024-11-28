package ru.markn.gpteam.servicies

import org.springframework.security.core.userdetails.UserDetailsService
import ru.markn.gpteam.dtos.AssistantDto
import ru.markn.gpteam.dtos.AuthDto
import ru.markn.gpteam.dtos.UpdateAssistantDto
import ru.markn.gpteam.models.Assistant

interface AssistantService : UserDetailsService {
    fun getAssistantByName(name: String): Assistant
    fun getAssistantByApiKey(apiKey: String): AssistantDto
    fun addAssistant(authDto: AuthDto): Assistant
    fun updateAssistant(updateAssistantDto: UpdateAssistantDto): Assistant
}