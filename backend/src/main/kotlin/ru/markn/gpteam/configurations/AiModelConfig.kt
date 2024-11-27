package ru.markn.gpteam.configurations

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.function.client.WebClient

@Configuration
class AiModelConfig(
    @Value("\${ai.url}")
    private val aiUrl: String,
    @Value("\${ai.apikey}")
    private val apiKey: String
) {
    @Bean
    fun aiModelClient(): WebClient = WebClient.builder()
        .baseUrl(aiUrl)
        .defaultHeader("Authorization", "Bearer $apiKey")
        .build()
}