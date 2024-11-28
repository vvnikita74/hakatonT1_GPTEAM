package ru.markn.gpteam.clients

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.client.request.forms.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.json.Json
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import org.springframework.web.multipart.MultipartFile
import ru.markn.gpteam.dtos.DbConnectDto
import ru.markn.gpteam.dtos.FileDecodeResponseDto
import ru.markn.gpteam.dtos.UrlParseDto
import ru.markn.gpteam.exceptions.ExternalServiceRequestException

@Component
class FileDecoderClient(
    @Value("\${file.decoder.url}")
    private val fileDecoderUrl: String
) {
    private val client = HttpClient {
        install(ContentNegotiation) {
            json(
                json = Json {
                    ignoreUnknownKeys = true
                    prettyPrint = true
                },
                contentType = ContentType.Any
            )
        }
    }

    fun decodeFiles(files: List<MultipartFile>): List<FileDecodeResponseDto> = runBlocking {
        files.map { file ->
            async {
                runCatching {
                    client.submitFormWithBinaryData(
                        url = "$fileDecoderUrl/api/v1/decode-file/",
                        formData = formData {
                            append("file", file.bytes, Headers.build {
                                append(HttpHeaders.ContentType, "text/plain")
                                append(HttpHeaders.ContentDisposition, "filename=\"${file.originalFilename}\"")
                            })
                        }
                    ).body<FileDecodeResponseDto>()
                }.getOrElse {
                    throw ExternalServiceRequestException("File decoding failed: ${it.message}")
                }
            }
        }.map { it.await() }
    }

    fun dbConnect(dbConnectDto: DbConnectDto): FileDecodeResponseDto = runBlocking {
        runCatching {
            client.post("$fileDecoderUrl/api/v1/postgres-connect/") {
                contentType(ContentType.Application.Json)
                setBody(dbConnectDto)
            }.body<FileDecodeResponseDto>()
        }.getOrElse {
            throw ExternalServiceRequestException("DB connection failed: ${it.message}")
        }
    }

    fun urlParse(urlParseDto: UrlParseDto): FileDecodeResponseDto = runBlocking {
        runCatching {
            client.post("$fileDecoderUrl/api/v1/parse/") {
                contentType(ContentType.Application.Json)
                setBody(urlParseDto)
            }.body<FileDecodeResponseDto>()
        }.getOrElse {
            throw ExternalServiceRequestException("URL parsing failed: ${it.message}")
        }
    }
}