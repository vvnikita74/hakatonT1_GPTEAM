package ru.markn.gpteam.servicies

import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import ru.markn.gpteam.dtos.UserDto
import ru.markn.gpteam.exceptions.EntityAlreadyExistsException
import ru.markn.gpteam.exceptions.EntityNotFoundException
import ru.markn.gpteam.models.User
import ru.markn.gpteam.repositories.UserRepository

@Transactional
@Service
class UserServiceImpl(
    private val roleService: RoleService,
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder
) : UserService {

    override val users: List<User>
        get() = userRepository.findAll()
            .sortedBy { it.id }

    override fun getUserById(id: Long): User = userRepository.findById(id)
        .orElseThrow { EntityNotFoundException("User with id: $id not found") }

    override fun getUserByUsername(username: String): User = userRepository.findByUsername(username)
        .orElseThrow { EntityNotFoundException("User with username: $username not found") }

    override fun findUsersByUsernameContains(username: String): List<User> =
        userRepository.findUsersByUsernameContains(username)

    override fun getUsersById(ids: List<Long>): List<User> =
        userRepository.findAllById(ids).also {
            ids.forEach { id ->
                if (!users.map { it.id }.contains(id)) {
                    throw EntityNotFoundException("User with id: $id not found")
                }
            }
        }

    override fun getUsersByUsernameIn(usernames: List<String>): List<User> =
        userRepository.findUsersByUsernameIn(usernames).also {
            usernames.forEach { username ->
                if (!users.map { it.username }.contains(username)) {
                    throw EntityNotFoundException("User with username: $username not found")
                }
            }
        }

    override fun addUser(userDto: UserDto): User {
        if (userRepository.existsByUsername(userDto.username)) {
            throw EntityAlreadyExistsException("User with username ${userDto.username} exist")
        }
        val user = User(
            username = userDto.username,
            password = passwordEncoder.encode(userDto.password),
            roles = listOf(roleService.roleUser)
        )
        return userRepository.save(user)
    }

    override fun deleteUser(id: Long) {
        if (!userRepository.existsById(id)) {
            throw EntityNotFoundException("User with id: $id not found")
        }
        userRepository.deleteById(id)
    }

    override fun loadUserByUsername(username: String): UserDetails {
        val user = userRepository.findByUsername(username)
            .orElseThrow { EntityNotFoundException("User with username: $username not found") }
        return org.springframework.security.core.userdetails.User(
            user.username,
            user.password,
            user.roles.map { role -> SimpleGrantedAuthority(role.name) }
        )
    }
}