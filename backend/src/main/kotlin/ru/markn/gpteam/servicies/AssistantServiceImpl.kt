package ru.markn.gpteam.servicies

import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import ru.markn.gpteam.dtos.AssistantDto
import ru.markn.gpteam.exceptions.EntityAlreadyExistsException
import ru.markn.gpteam.exceptions.EntityNotFoundException
import ru.markn.gpteam.models.Assistant
import ru.markn.gpteam.repositories.AssistantRepository

@Transactional
@Service
class AssistantServiceImpl(
    private val assistantRepository: AssistantRepository,
    private val passwordEncoder: PasswordEncoder
) : AssistantService {

    override val assistants: List<Assistant>
        get() = assistantRepository.findAll()
            .sortedBy { it.id }

    override fun getAssistantById(id: Long): Assistant = assistantRepository.findById(id)
        .orElseThrow { EntityNotFoundException("Assistant with id: $id not found") }

    override fun getAssistantByName(name: String): Assistant = assistantRepository.findByName(name)
        .orElseThrow { EntityNotFoundException("Assistant with name: $name not found") }

    override fun findAssistantsByNameContains(name: String): List<Assistant> =
        assistantRepository.findAssistantsByNameContains(name)

    override fun getAssistantsByIds(ids: List<Long>): List<Assistant> =
        assistantRepository.findAllById(ids).also {
            ids.forEach { id ->
                if (!assistants.map { it.id }.contains(id)) {
                    throw EntityNotFoundException("Assistant with id: $id not found")
                }
            }
        }

    override fun addAssistant(assistantDto: AssistantDto): Assistant {
        if (assistantRepository.existsByName(assistantDto.assistant)) {
            throw EntityAlreadyExistsException("Assistant with name ${assistantDto.assistant} exist")
        }
        val assistant = Assistant(
            name = assistantDto.assistant,
            password = passwordEncoder.encode(assistantDto.password)
        )
        return assistantRepository.save(assistant)
    }

    override fun deleteAssistant(id: Long) {
        if (!assistantRepository.existsById(id)) {
            throw EntityNotFoundException("Assistant with id: $id not found")
        }
        assistantRepository.deleteById(id)
    }

    override fun loadUserByUsername(name: String): UserDetails {
        val user = assistantRepository.findByName(name)
            .orElseThrow { EntityNotFoundException("Assistant with name: $name not found") }
        return User(
            user.name,
            user.password,
            listOf(SimpleGrantedAuthority("ROLE_ASSISTANT"))
        )
    }
}