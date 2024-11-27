package ru.markn.gpteam.servicies

import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import ru.markn.gpteam.dtos.AuthDto
import ru.markn.gpteam.dtos.UpdateAssistantDto
import ru.markn.gpteam.exceptions.EntityAlreadyExistsException
import ru.markn.gpteam.exceptions.EntityNotFoundException
import ru.markn.gpteam.exceptions.IncorrectArgumentException
import ru.markn.gpteam.models.Assistant
import ru.markn.gpteam.models.Prompt
import ru.markn.gpteam.repositories.AssistantRepository
import ru.markn.gpteam.repositories.PromptRepository
import kotlin.jvm.optionals.getOrNull

@Transactional
@Service
class AssistantServiceImpl(
    private val assistantRepository: AssistantRepository,
    private val promptRepository: PromptRepository,
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

    override fun addAssistant(authDto: AuthDto): Assistant {
        if (assistantRepository.existsByName(authDto.assistant)) {
            throw EntityAlreadyExistsException("Assistant with name ${authDto.assistant} exist")
        }
        val assistant = Assistant(
            name = authDto.assistant,
            password = passwordEncoder.encode(authDto.password)
        )
        return assistantRepository.save(assistant)
    }

    override fun updateAssistant(updateAssistantDto: UpdateAssistantDto): Assistant {
        if (updateAssistantDto.assistant == null) {
            throw IllegalArgumentException("Assistant name can't be blank")
        }
        return assistantRepository.getByName(updateAssistantDto.assistant).let { assistant ->
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
            // TODO: Реализация внешнего сервиса для получения контента файлов
            val filePrompts = updateAssistantDto.files?.map { file ->
                Prompt(
                    name = file.originalFilename ?: throw IncorrectArgumentException("File name can't be blank"),
                    content = file.bytes.toString(),
                    assistant = assistant
                )
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
            assistantRepository.save(
                assistant.copy(
                    styles = updateAssistantDto.styles ?: assistant.styles,
                    prompts = newPrompts
                )
            )
        }
    }

    override fun deleteAssistant(id: Long) {
        if (!assistantRepository.existsById(id)) {
            throw EntityNotFoundException("Assistant with id: $id not found")
        }
        assistantRepository.deleteById(id)
    }

    override fun loadUserByUsername(name: String): UserDetails {
        val assistant = assistantRepository.findByName(name)
            .orElseThrow { EntityNotFoundException("Assistant with name: $name not found") }
        return User(
            assistant.name,
            assistant.password,
            listOf(SimpleGrantedAuthority("ROLE_ASSISTANT"))
        )
    }
}