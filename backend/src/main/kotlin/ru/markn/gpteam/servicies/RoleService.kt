package ru.markn.gpteam.servicies

import ru.markn.gpteam.models.Role

interface RoleService {
    val roleUser: Role
    val roleAdmin: Role
    fun getRoleById(id: Long): Role
    fun getRoleByName(name: String): Role
}