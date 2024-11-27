package ru.markn.gpteam.controllers

import org.springframework.http.HttpStatus
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice
import ru.markn.gpteam.exceptions.EntityAlreadyExistsException
import ru.markn.gpteam.exceptions.EntityNotFoundException
import ru.markn.gpteam.exceptions.ExternalServiceRequestException
import ru.markn.gpteam.exceptions.IncorrectArgumentException

@RestControllerAdvice
class ExceptionRestController {
    @ExceptionHandler(EntityNotFoundException::class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    fun entityNotFoundHandler(ex: EntityNotFoundException) = ex.message

    @ExceptionHandler(EntityAlreadyExistsException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun entityAlreadyExistsHandler(ex: EntityAlreadyExistsException) = ex.message

    @ExceptionHandler(IncorrectArgumentException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun incorrectArgumentHandler(ex: IncorrectArgumentException) = ex.message

    @ExceptionHandler(BadCredentialsException::class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    fun badCredentialsHandler(ex: BadCredentialsException) = ex.message

    @ExceptionHandler(ExternalServiceRequestException::class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    fun externalServiceRequestExceptionHandler(ex: ExternalServiceRequestException) = ex.message

    @ExceptionHandler(MethodArgumentNotValidException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun methodArgumentNotValidHandler(ex: MethodArgumentNotValidException) =
        ex.bindingResult.allErrors.joinToString("\n") {"${it.defaultMessage}" }
}