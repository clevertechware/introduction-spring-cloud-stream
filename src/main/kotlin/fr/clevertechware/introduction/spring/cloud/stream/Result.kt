package fr.clevertechware.introduction.spring.cloud.stream

data class Result(
    val id: String,
    val timestamp: Long,
    val content: String,
    val sender: String,
    val receiver: String
)
