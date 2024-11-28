package ru.markn.gpteam.configurations

import org.springframework.boot.autoconfigure.cache.RedisCacheManagerBuilderCustomizer
import org.springframework.cache.annotation.EnableCaching
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.redis.cache.RedisCacheConfiguration
import org.springframework.data.redis.cache.RedisCacheManager.RedisCacheManagerBuilder
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer
import org.springframework.data.redis.serializer.RedisSerializationContext.SerializationPair
import java.time.Duration

@Configuration
@EnableCaching(proxyTargetClass = true)
class RedisConfig {

    companion object {
        const val ASSISTANT_API_KEY = "assistant:api"
    }

    @Bean
    fun redisCacheManagerBuilderCustomizer(): RedisCacheManagerBuilderCustomizer? =
        RedisCacheManagerBuilderCustomizer { builder: RedisCacheManagerBuilder ->
            builder.withCacheConfiguration(
                ASSISTANT_API_KEY,
                cacheConfiguration().entryTtl(Duration.ofMinutes(5))
            )
        }

    @Bean
    fun cacheConfiguration(): RedisCacheConfiguration = RedisCacheConfiguration
        .defaultCacheConfig()
        .disableCachingNullValues()
        .serializeValuesWith(SerializationPair.fromSerializer(GenericJackson2JsonRedisSerializer()))
}