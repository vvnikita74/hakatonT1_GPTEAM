package ru.markn.gpteam.dtos

import kotlinx.serialization.Serializable

@Serializable
data class DbConnectDto(
    val host: String,
    val port: Int,
    val username: String,
    val password: String,
    val database: String,
    val query: String
)
