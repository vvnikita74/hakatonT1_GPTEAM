package ru.markn.gpteam.servicies

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import ru.markn.gpteam.exceptions.EntityNotFoundException
import ru.markn.gpteam.models.Role
import ru.markn.gpteam.repositories.RoleRepository

@Transactional
@Service
class RoleServiceImpl(
    private val roleRepository: RoleRepository
) : RoleService {

    companion object {
        private const val ROLE_USER = "ROLE_USER"
        private const val ROLE_ADMIN = "ROLE_ADMIN"
    }

    override val roleUser: Role
        get() = roleRepository.getByName(ROLE_USER)

    override val roleAdmin: Role
        get() = roleRepository.getByName(ROLE_ADMIN)

    override fun getRoleById(id: Long): Role = roleRepository.findById(id)
        .orElseThrow { EntityNotFoundException("Role with id $id not found") }

    override fun getRoleByName(name: String): Role = roleRepository.findByName(name)
        .orElseThrow { EntityNotFoundException("Role with name $name not found") }
}