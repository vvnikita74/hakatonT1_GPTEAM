package ru.markn.gpteam.servicies

import org.springframework.cache.annotation.Cacheable
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import ru.markn.gpteam.clients.FileDecoderClient
import ru.markn.gpteam.configurations.RedisConfig
import ru.markn.gpteam.dtos.AssistantDto
import ru.markn.gpteam.dtos.AuthDto
import ru.markn.gpteam.dtos.UpdateAssistantDto
import ru.markn.gpteam.exceptions.EntityAlreadyExistsException
import ru.markn.gpteam.exceptions.EntityNotFoundException
import ru.markn.gpteam.exceptions.IncorrectArgumentException
import ru.markn.gpteam.models.Assistant
import ru.markn.gpteam.models.Prompt
import ru.markn.gpteam.models.Role
import ru.markn.gpteam.models.User
import ru.markn.gpteam.repositories.AssistantRepository
import ru.markn.gpteam.repositories.PromptRepository
import ru.markn.gpteam.repositories.UserRepository
import ru.markn.gpteam.utils.toDto
import java.security.SecureRandom
import java.util.*
import kotlin.jvm.optionals.getOrNull

@Transactional
@Service
class AssistantServiceImpl(
    private val assistantRepository: AssistantRepository,
    private val userRepository: UserRepository,
    private val promptRepository: PromptRepository,
    private val fileDecoderClient: FileDecoderClient,
    private val passwordEncoder: PasswordEncoder
) : AssistantService {

    override fun getAssistantByName(name: String): Assistant = assistantRepository.findByUserName(name)
        .orElseThrow { EntityNotFoundException("Assistant with name: $name not found") }

    @Cacheable(value = [RedisConfig.ASSISTANT_API_KEY], key = "#apiKey")
    override fun getAssistantByApiKey(apiKey: String): AssistantDto = assistantRepository.findByApiKey(apiKey)
        .orElseThrow { EntityNotFoundException("Assistant with apiKey: $apiKey not found") }.toDto()

    override fun addAssistant(authDto: AuthDto): Assistant {
        if (userRepository.existsByName(authDto.assistant)) {
            throw EntityAlreadyExistsException("Assistant with name ${authDto.assistant} exist")
        }
        val user = userRepository.save(
            User(
                name = authDto.assistant,
                password = passwordEncoder.encode(authDto.password),
                roles = listOf(Role(name = "ROLE_USER"))
            )
        )
        val assistant = Assistant(
            apiKey = generateApiKey(),
            user = user
        )
        return assistantRepository.save(assistant)
    }

    override fun updateAssistant(updateAssistantDto: UpdateAssistantDto): Assistant {
        if (updateAssistantDto.assistant == null) {
            throw IllegalArgumentException("Assistant name can't be blank")
        }
        val assistant = getAssistantByName(updateAssistantDto.assistant)

        updateAssistantDto.styles?.let { styles ->
            if (styles.isEmpty()) {
                throw IllegalArgumentException("Styles can't be empty")
            }
        }
        val textPrompt = updateAssistantDto.text?.let { newContent ->
            promptRepository.findByName("text").getOrNull()?.run {
                copy(content = newContent)
            } ?: Prompt(
                name = "text",
                content = newContent,
                assistant = assistant
            )
        }
        val deletedPrompts = updateAssistantDto.deletedFiles?.map { fileDto ->
            assistant.prompts.find { prompt ->
                prompt.id == fileDto.id
            }?.apply {
                if (name != fileDto.filename) {
                    throw IncorrectArgumentException("File with id: ${fileDto.id} name mismatch")
                }
            } ?: throw EntityNotFoundException("File with id: ${fileDto.id} not found")
        }
        val filePrompts = updateAssistantDto.files?.let { files ->
            fileDecoderClient.decodeFiles(files).map {
                Prompt(
                    name = it.filename,
                    content = it.content,
                    assistant = assistant
                )
            }
        }
        val newPrompts = assistant.prompts.toMutableList().apply {
            textPrompt?.let {
                find { prompt -> prompt.name == "text" }?.let { oldTextPrompt ->
                    remove(oldTextPrompt)
                }
                add(textPrompt)
            }
            deletedPrompts?.let {
                promptRepository.deleteAll(it)
                removeAll(it)
            }
            filePrompts?.run(::addAll)
        }
        return assistantRepository.save(
            assistant.copy(
                apiKey = assistant.apiKey.ifBlank(::generateApiKey),
                styles = updateAssistantDto.styles ?: assistant.styles,
                prompts = newPrompts
            )
        )
    }

    override fun loadUserByUsername(name: String): UserDetails {
        val user = userRepository.findByName(name)
            .orElseThrow { EntityNotFoundException("Assistant with name: $name not found") }
        return org.springframework.security.core.userdetails.User(
            user.name,
            user.password,
            user.roles.map { role -> SimpleGrantedAuthority(role.name) }
        )
    }

    private fun generateApiKey(): String = ByteArray(32).let {
        SecureRandom().nextBytes(it)
        Base64.getUrlEncoder().withoutPadding().encodeToString(it)
    }
}