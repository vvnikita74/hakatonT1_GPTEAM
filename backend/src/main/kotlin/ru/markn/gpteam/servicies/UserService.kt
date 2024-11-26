package ru.markn.gpteam.servicies

import org.springframework.security.core.userdetails.UserDetailsService
import ru.markn.gpteam.dtos.UserDto
import ru.markn.gpteam.models.User

interface UserService : UserDetailsService {
    val users: List<User>
    fun getUserById(id: Long): User
    fun getUsersById(ids: List<Long>): List<User>
    fun getUserByUsername(username: String): User
    fun findUsersByUsernameContains(username: String): List<User>
    fun getUsersByUsernameIn(usernames: List<String>): List<User>
    fun addUser(userDto: UserDto): User
    fun deleteUser(id: Long)
}