package ru.markn.gpteam.repositories

import org.springframework.data.jpa.repository.JpaRepository
import ru.markn.gpteam.models.Role

interface RoleRepository : JpaRepository<Role, Long> {
    fun getByName(name: String): Role
}