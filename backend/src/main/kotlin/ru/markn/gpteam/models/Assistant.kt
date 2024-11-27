package ru.markn.gpteam.models

import jakarta.persistence.*

@Entity
@Table(name = "assistants")
data class Assistant(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    val id: Long = 0,
    @Column(name = "name", nullable = false, unique = true)
    val name: String,
    @Column(name = "password", nullable = false)
    val password: String,
    @Column(name = "styles", nullable = false)
    val styles: String = "{}",
    @Column(name = "api_key")
    val apiKey: String = "",
    @OneToMany(mappedBy = "assistant", cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    val prompts: List<Prompt> = emptyList()
)