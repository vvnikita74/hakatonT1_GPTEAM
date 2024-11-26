package ru.markn.gpteam

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class GpteamApplication

fun main(args: Array<String>) {
    runApplication<GpteamApplication>(*args)
}
