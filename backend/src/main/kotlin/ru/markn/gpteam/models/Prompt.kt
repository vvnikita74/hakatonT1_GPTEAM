package ru.markn.gpteam.models

import jakarta.persistence.*

@Entity
@Table(name = "prompts")
data class Prompt(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    val id: Long = 0,
    @Column(name = "name", nullable = false)
    val name: String,
    @Column(name = "content", nullable = false)
    val content: String,
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "assistant_id")
    val assistant: Assistant
)
