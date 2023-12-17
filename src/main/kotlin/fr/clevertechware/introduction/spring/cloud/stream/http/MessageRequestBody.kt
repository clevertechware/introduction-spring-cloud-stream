package fr.clevertechware.introduction.spring.cloud.stream.http

import jakarta.validation.constraints.NotBlank

data class MessageRequestBody(
    @field:NotBlank
    val content: String,
    val receiver: String
)